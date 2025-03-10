<template>
  <div class="detailPat">
    <h2>Détails du Patient</h2>
    <p>ID: {{ patient?.id }}</p>
    <p>patId: {{ patient?.patId }}</p>
    <p>Nom: {{ patient?.lastName }}</p>
    <p>Prénom: {{ patient?.firstName }}</p>
    <p>Date de naissance: {{ patient?.birthDay }}</p>
    <p>Sexe: {{ patient?.gender }}</p>
    <p>Adresse: {{ patient?.address }}</p>
    <p>Téléphone: {{ patient?.phone }}</p>
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
        <textarea type="text" id="noteToADD" v-model="noteToADD"></textarea>
        <div><button @click="addNote" >Ajouter note</button></div>
      </div>
    </div>
  </div>

  <div class="BilanPat">
    <h2>Bilan du patient</h2>
    <button @click="generateBilan">Générer bilan</button>
    {{bilan}}
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import PatientinformationService from "@/services/PatientinformationService.js";
import PatientHistoriqueService from "@/services/PatientHistoriqueService.js";
import PatientBilanService from "@/services/PatientBilanService.js";
import PatientInformation from "@/model/PatientInformation.js";
import PatientHistorique from "@/model/PatientHistorique.js";
import RequestBilan from "@/model/RequestBilan.js";

const route = useRoute();
const router = useRouter();
const patient = ref(null);
const patientNoteList = ref(null);
const noteToADD = ref(null);
const bilan = ref(null);

// Fonction pour récupérer les données du patient
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
    console.info("valeur " + patient.value.patId, patient.value.lastName, noteToADD.value)
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
  try {
   bilan.value = await PatientBilanService.getPatientBilan(requestBilan)
  } catch (error) {
    console.error("valeur " + patient.value.id, patientNoteListToBilan, patient.value.birthDay, patient.value.gender)
    console.error("Erreur lors du chargement du bilan " +error)
  }
}
</script>
<style>
.detailPat{
  text-align: left;
}

.historiquePat{
  text-align: left;
}

.BilanPat{
  text-align: left;
}
</style>
