<template>
  <div>
    <h2>Détails du Patient</h2>
    <p><strong>ID:</strong> {{ patient?.id }}</p>
    <p><strong>Nom:</strong> {{ patient?.lastName }}</p>
    <p><strong>Prénom:</strong> {{ patient?.firstName }}</p>
    <p><strong>Date de naissance:</strong> {{ patient?.birthDay }}</p>
    <p><strong>Sexe:</strong> {{ patient?.gender }}</p>
    <p><strong>Adresse:</strong> {{ patient?.address }}</p>
    <p><strong>Téléphone:</strong> {{ patient?.phone }}</p>

    <button @click="goBack">Retour</button>
  </div>

  <div>
    <div class="container">
      <h2 class="text-center">Historique du patient</h2>
      <button @click="getHistorique">Afficher historique</button>
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Id</th>
          <th>patId</th>
          <th>Patient</th>
          <th>Note</th>

        </tr>
        </thead>
        <tbody>
        <tr v-for="(patientHistorique, index) in patientNoteList" :key="index">
          <td>{{ patientHistorique.id }}</td>
          <td>{{ patientHistorique.patId}}</td>
          <td>{{ patientHistorique.patient}}</td>
          <td>{{ patientHistorique.note }}</td>
          <td>
          </td>
        </tr>
        </tbody>
      </table>
      <div>
        <h3>Nouvelle note</h3>
        <label for="noteToADD">Note :</label>
        <input type="text" id="noteToADD" v-model="noteToADD"><br><br>
        <button @click="addNote" >Ajouter note</button>
      </div>
    </div>
  </div>

  <div>
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

// Fonction pour revenir en arrière
const goBack = () => {
  router.go(-1);
};

const getHistorique = async () => {
  try {
    patientNoteList.value = await PatientHistoriqueService.getPatientHistoriqueByPatId("2")
  } catch (error) {
    console.error("Erreur lors de la recherche de l'historique du patient", error)
  }
}

const addNote = async () => {
  try {
    await PatientHistoriqueService.addNote(patient.value.id, patient.value.lastName, noteToADD.value)
  } catch (error) {
    console.error("Erreur lors de l'ajout de la note'", error)
  }
  noteToADD.value = "";
  await getHistorique();
}

const generateBilan = async () => {
  try {
   bilan.value = await PatientBilanService.getPatientBilan(patient.value.id,patientNoteList,patient.value.birthDay,patient.value.gender)
  } catch (error) {
    console.error("valeur " + patient.value.id, patientNoteList, patient.value.birthDay, patient.value.gender)
    console.error("Erreur lors du chargement du bilan " +error)
  }
}
</script>
