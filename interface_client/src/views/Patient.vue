<template xmlns="http://www.w3.org/1999/html">
  <div class="detailPat">
    <h2>Détails du Patient</h2>
    <p>Numéro patient: {{ patInformation?.patId }}</p>
    <p>Nom: {{ patInformation?.lastName }}</p>
    <p>Prénom: {{ patInformation?.firstName }}</p>
    <p>Date de naissance: {{ patInformation?.birthDay }}</p>
    <p>Sexe: {{ patInformation?.gender }}</p>
    <p>Adresse: {{ patInformation?.address }}</p>
    <p>Téléphone: {{ patInformation?.phone }}</p>
  </div>

  <div class="body">
    <h2>Modifier les informations du patient</h2>
    <div class="form-container">
      <form @submit.prevent="updatePatInformation">

        <input type="text" id="lastName" v-model="patInformationToUpdate.lastName" placeholder="Nom"><br><br>

        <input type="text" id="firstName" v-model="patInformationToUpdate.firstName" placeholder="Prénom"><br><br>

        <input type="date" id="birthDay" v-model="patInformationToUpdate.birthDay"><br><br>

        <select id="gender" v-model="patInformationToUpdate.gender" >
          <option value="">Sexe</option>
          <option value="M">Homme</option>
          <option value="F">Femme</option>
        </select><br><br>

        <input type="text" id="address" v-model="patInformationToUpdate.address" placeholder="Adresse"><br><br>

        <input type="text" id="phone" v-model="patInformationToUpdate.phone" placeholder="Téléphonne"><br><br>

        <button type="button" @click="updatePatInformation">Mettre à jour</button>
      </form>
    </div>


  <div>
    <div class="historiquePat">
      <h2>Historique du patient</h2>
      <table class="table table-striped">
        <tbody>
        <tr v-for="(patientHistorique, index) in patHistory" :key="index">
          <td>{{ patientHistorique.note }}</td>
        </tr>
        </tbody>
      </table>
      <div class="NouvelleNote">
        <h2>Nouvelle note</h2>
        <textarea type="text" id="noteToADD" size="100" v-model="noteToADD"></textarea>

        <div><button @click="addNote" >Ajouter note</button></div>
      </div>
    </div>
  </div>

  <div class="BilanPat">
    <h2>PatAssessment du patient</h2>
    <div>{{assessmentMessage}}</div>
    <button @click="generatePatAssessment">Générer patAssessment</button>

  </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute} from "vue-router";
import PatientinformationService from "@/services/PatInformationService.js";
import PatHistoryService from "@/services/PatientHistoryService.js";
import PatAssessmentService from "@/services/PatAssessmentService.js";
import PatInformation from "@/model/PatInformation.js";
import PatHistory from "@/model/PatHistory.js";
import RequestPatAssessment from "@/model/RequestPatAssessment.js";
import PatAssessment from "@/model/PatAssessment.js";


const route = useRoute();

const patInformation = ref(null);
const patInformationToUpdate = new PatInformation();
const patHistory = ref(null);
const noteToADD = ref(null);
const assessmentMessage = ref(null);
const errors = ref({});

onMounted(() => {
  try {
    if (route.query.data) {
      patInformation.value = JSON.parse(decodeURIComponent(route.query.data));
      patInformationToUpdate.patId = patInformation.value.patId;
      patInformationToUpdate.lastName = patInformation.value.lastName ;
      patInformationToUpdate.firstName = patInformation.value.firstName;
      patInformationToUpdate.birthDay = patInformation.value.birthDay;
      patInformationToUpdate.gender = patInformation.value.gender;
      patInformationToUpdate.address = patInformation.value.address;
      patInformationToUpdate.phone = patInformation.value.phone;

      getPatHistory()
    } else {
      console.warn("Aucune donnée de patient reçue.");
    }
  } catch (error) {
    console.error("Erreur lors du parsing des données du patient:", error);
  }
});

const updatePatInformation = async () => {
  const patientInformationToUpdate = new PatInformation();
  patientInformationToUpdate.patId = patInformationToUpdate.patId;
  patientInformationToUpdate.lastName = patInformationToUpdate.lastName;
  patientInformationToUpdate.firstName = patInformationToUpdate.firstName;
  patientInformationToUpdate.birthDay = patInformationToUpdate.birthDay;
  patientInformationToUpdate.gender = patInformationToUpdate.gender;
  patientInformationToUpdate.address = patInformationToUpdate.address;
  patientInformationToUpdate.phone = patInformationToUpdate.phone;

  try {
    await PatientinformationService.updatePatInformation(patInformation.value.patId, patientInformationToUpdate);
    patInformation.value = await PatientinformationService.getPatInformationByPatId(patientInformationToUpdate.patId);
    errors.value = {};
  } catch (error) {
    if (error.response && error.response.data) {
      errors.value = error.response.data;
    } else {
      console.error("Erreur lors de l'update du patient", error);
    }
  }



}

const getPatHistory = async () => {
  try {
    patHistory.value = await PatHistoryService.getPatHistoryByPatId(patInformation.value.patId)
  } catch (error) {
    console.error("Erreur lors de la recherche de l'historique du patient", error)
  }
}

const addNote = async () => {
  const patHistory = new PatHistory("", patInformation.value.patId, patInformation.value.lastName, noteToADD.value)
  try {
    await PatHistoryService.addNote(patHistory)
  } catch (error) {
    console.error("Erreur lors de l'ajout de la note'", error)
  }
  noteToADD.value = "";
  await getPatHistory();
}

const generatePatAssessment = async () => {
  const patientNoteListToPatAssessment = patHistory.value.map(patHistory => patHistory.note)
  const requestPatAssessment = new RequestPatAssessment(patInformation.value.patId, patientNoteListToPatAssessment, patInformation.value.birthDay, patInformation.value.gender)
  let patAssessment2 = new PatAssessment("","");
  try {
    patAssessment2 = await PatAssessmentService.getPatAssessment(requestPatAssessment);
    assessmentMessage.value = patAssessment2.riskLevel
  } catch (error) {
    console.error("valeur " + patInformation.value.id, patientNoteListToPatAssessment, patInformation.value.birthDay, patInformation.value.gender)
    console.error("Erreur lors du chargement du patAssessment " +error)
  }

}
</script>
<style>

h2 {
  text-align: center;
  margin-bottom: 5px;
}

button{
  margin-top: 5px;
}
.detailPat{
  text-align: left;
  margin-bottom: 10px;
}

.historiquePat{
  text-align: left;
  margin-bottom: 10px;
}

.NouvelleNote{
  text-align: center;
}

textarea{
  width: 100%;
  margin-bottom: 5px;
}

.BilanPat{

}

.updatePatient{
  margin-bottom: 10px;
}

label {



  font-weight: bold;
  margin-bottom: 5px;
}
</style>
