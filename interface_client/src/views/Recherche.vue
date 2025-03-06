<template>
  <div class="body">
    <h2>Recherche patient</h2>
    <form @submit.prevent="searchPatient">
      <label for="lastName">Nom :</label>
      <input type="text" id="lastName" v-model="lastName"><br><br>

      <label for="firstName">Prénom :</label>
      <input type="text" id="firstName" v-model="firstName"><br><br>

      <label for="birthDay">Date de naissance :</label>
      <input type="date" id="birthDay" v-model="birthDay"><br><br>

      <label for="gender">Sexe :</label>
      <select id="gender" v-model="gender">
        <option value="">Sélectionnez</option>
        <option value="M">Homme</option>
        <option value="F">Femme</option>
      </select><br><br>

      <label for="address">Adresse :</label>
      <input type="text" id="address" v-model="address"><br><br>

      <label for="phone">Téléphone :</label>
      <input type="text" id="phone" v-model="phone"><br><br>

      <button type="button" @click="searchPatient">Recherche patient</button>
    </form>

    <div class="container">
      <h2 class="text-center">Résultat</h2>
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Nom</th>
          <th>Prénom</th>
          <th>Date de naissance</th>
          <th>Sexe</th>
          <th>Adresse</th>
          <th>Téléphone</th>
          <th>Selection</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(patient, index) in patientList" :key="index">
          <td>{{ patient.lastName }}</td>
          <td>{{ patient.firstName }}</td>
          <td>{{ patient.birthDay }}</td>
          <td>{{ patient.gender }}</td>
          <td>{{ patient.address }}</td>
          <td>{{ patient.phone }}</td>
          <td>
            <input type="radio"
                   v-model="selectedPatient"
                   :value="patient"
                   name="patientRadio"
                   @change="updateSelectedPatient(patient)">
            <button @click="goToPatientPage(patient)">Voir Détails</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import PatientInformationService from "@/services/PatientinformationService.js";

// Déclaration des variables réactives
const router = useRouter();
const lastName = ref("");
const firstName = ref("");
const birthDay = ref("");
const gender = ref("");
const address = ref("");
const phone = ref("");
const patientList = ref([]);
const selectedPatient = ref(null);

// Méthode de recherche des patients
const searchPatient = async () => {
  try {
    patientList.value = await PatientInformationService.getPatientByAllInformation(
        lastName.value, firstName.value, birthDay.value, gender.value, address.value, phone.value
    );
  } catch (error) {
    console.error("Erreur lors de la recherche des patients:", error);
  }
};

// Méthode de sélection du patient
const updateSelectedPatient = (patient) => {
  selectedPatient.value = patient ? { ...patient } : null;
  console.log("Patient sélectionné:", selectedPatient.value);
};

// Navigation vers la page du patient
const goToPatientPage = (patient) => {
  if (!patient || !patient.id) {
    console.error("Erreur: patient invalide", patient);
    return;
  }

  router.push({
    name: 'PatientPage',
    params: { id: String(patient.id) }, // S'assurer que l'ID est une chaîne
    query: { data: encodeURIComponent(JSON.stringify(patient)) } // Éviter les erreurs JSON
  });
};
</script>

<style scoped>
/* Ajoutez ici vos styles si nécessaire */
</style>
