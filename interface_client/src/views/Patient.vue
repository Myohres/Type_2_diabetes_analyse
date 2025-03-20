<template xmlns="http://www.w3.org/1999/html">
  <div class="detailPat">
    <h2>Détails du Patient</h2>
    <p>Numéro patient: {{ patient?.patId }}</p>
    <p>Nom: {{ patient?.lastName }}</p>
    <p>Prénom: {{ patient?.firstName }}</p>
    <p>Date de naissance: {{ patient?.birthDay }}</p>
    <p>Sexe: {{ patient?.gender }}</p>
    <p>Adresse: {{ patient?.address }}</p>
    <p>Téléphone: {{ patient?.phone }}</p>
  </div>

  <div class="updatePatient">
    <h2>Modifier les informations du patient</h2>
    <div v-if="patient">
      <div v-for="(value, key) in patient" :key="key" class="input-group">
        <label :for="key">{{ key }}</label>
        <input
            :id="key"
            type="text"
            v-model="patient[key]"
            :class="{'is-invalid': errors[key]}"
        />
        <!-- Affichage des erreurs -->
        <div v-if="errors[key]" class="error-message">
          {{ errors[key] }}
        </div>
      </div>

      <button @click="updatePatientInformation">Mettre à jour</button>
    </div>
    <p v-else>Chargement des données...</p>
  </div>


  <div>
    <div class="historiquePat">
      <h2>Historique du patient</h2>
      <table class="table table-striped">
        <tbody>
        <tr v-for="(patientHistorique, index) in patientNoteList" :key="index">
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
    <h2>Bilan du patient</h2>
    <div>{{bilanMessage}}</div>
    <button @click="generateBilan">Générer bilan</button>

  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute} from "vue-router";
import PatientinformationService from "@/services/PatientinformationService.js";
import PatientHistoriqueService from "@/services/PatientHistoriqueService.js";
import PatientBilanService from "@/services/PatientBilanService.js";
import PatientInformation from "@/model/PatientInformation.js";
import PatientHistorique from "@/model/PatientHistorique.js";
import RequestBilan from "@/model/RequestBilan.js";
import Bilan from "@/model/Bilan.js";


const route = useRoute();
const patient = ref(null);
const patientNoteList = ref(null);
const noteToADD = ref(null);
const bilanMessage = ref(null);
const errors = ref({});

onMounted(() => {
  try {
    if (route.query.data) {
      patient.value = JSON.parse(decodeURIComponent(route.query.data));
      getHistorique()
    } else {
      console.warn("Aucune donnée de patient reçue.");
    }
  } catch (error) {
    console.error("Erreur lors du parsing des données du patient:", error);
  }
});

const updatePatientInformation = async () => {
  const patientInformationToUpdate = new PatientInformation();
  patientInformationToUpdate.patId = patient.value.patId;
  patientInformationToUpdate.lastName = patient.value.lastName;
  patientInformationToUpdate.firstName = patient.value.firstName;
  patientInformationToUpdate.birthDay = patient.value.birthDay;
  patientInformationToUpdate.gender = patient.value.gender;
  patientInformationToUpdate.address = patient.value.address;
  patientInformationToUpdate.phone = patient.value.phone;

  try {
    await PatientinformationService.updatePatientInformation(patient.value.patId, patientInformationToUpdate);
    errors.value = {};
  } catch (error) {
    if (error.response && error.response.data) {
      errors.value = error.response.data;
    } else {
      console.error("Erreur lors de l'update du patient", error);
    }
  }
}

const getHistorique = async () => {
  try {
    patientNoteList.value = await PatientHistoriqueService.getPatientHistoriqueByPatId(patient.value.patId)
  } catch (error) {
    console.error("Erreur lors de la recherche de l'historique du patient", error)
  }
}

const addNote = async () => {
  const patientHistorique = new PatientHistorique("", patient.value.patId, patient.value.lastName, noteToADD.value)
  try {
    await PatientHistoriqueService.addNote(patientHistorique)
  } catch (error) {
    console.error("Erreur lors de l'ajout de la note'", error)
  }
  noteToADD.value = "";
  await getHistorique();
}

const generateBilan = async () => {
  const patientNoteListToBilan = patientNoteList.value.map(patientHistorique => patientHistorique.note)
  const requestBilan = new RequestBilan(patient.value.patId,patientNoteListToBilan, patient.value.birthDay, patient.value.gender)
  let bilan2 = new Bilan("","");
  try {
    bilan2 = await PatientBilanService.getPatientBilan(requestBilan);
    bilanMessage.value = bilan2.riskLevel
  } catch (error) {
    console.error("valeur " + patient.value.id, patientNoteListToBilan, patient.value.birthDay, patient.value.gender)
    console.error("Erreur lors du chargement du bilan " +error)
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
