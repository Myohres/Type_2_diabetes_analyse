<template>
  <div class="body">
    <h2>Recherche patient</h2>
    <div class="form-container">
    <form @submit.prevent="searchPatient">

      <input type="text" id="patId" v-model="patId" placeholder="Numéro patient"><br><br>

      <input type="text" id="lastName" v-model="lastName" placeholder="Nom"><br><br>

      <input type="text" id="firstName" v-model="firstName" placeholder="Prénom"><br><br>

      <input type="date" id="birthDay" v-model="birthDay"><br><br>

      <select id="gender" v-model="gender" >
        <option value="">Sexe</option>
        <option value="M">Homme</option>
        <option value="F">Femme</option>
      </select><br><br>

      <input type="text" id="address" v-model="address" placeholder="Adresse"><br><br>

      <input type="text" id="phone" v-model="phone" placeholder="Téléphonne"><br><br>

      <button type="button" @click="searchPatient">Recherche patient</button>
    </form>
    </div>
    <div class="container">
      <h2 class="text-center">Résultat</h2>
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Numéro patient</th>
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
        <tr v-for="patient in patientList" :key="patient.patId">
          <td>{{patient.patId}}</td>
          <td>{{patient.lastName}}</td>
          <td>{{patient.firstName}}</td>
          <td>{{patient.birthDay}}</td>
          <td>{{patient.gender}}</td>
          <td>{{patient.address}}</td>
          <td>{{patient.phone}}</td>
          <td>
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
const patId = ref("");
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
    patientList.value = await PatientInformationService.getPatientByAllInformation(patId.value,
        lastName.value, firstName.value, birthDay.value, gender.value, address.value, phone.value
    );
  } catch (error) {
    console.error("Erreur lors de la recherche des patients:", error);
  }
};

const goToPatientPage = (patient) => {
  if (!patient || !patient.patId) {
    console.error("Erreur: patient invalide", patient);
    return;
  }

  router.push({
    name: 'PatientPage',
    params: { patId: String(patient.patId) },
    query: { data: encodeURIComponent(JSON.stringify(patient)) }
  });
};
</script>

<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 300px;
  margin: auto;
}
</style>
