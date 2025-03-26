<template>
  <div class="body">
    <h2>Recherche patient</h2>
    <form @submit.prevent="submitForm">
      <label for="lastName">Nom :</label>
      <input type="text" id="lastName" v-model="lastName"><br><br>

      <label for="firstName">Prénom :</label>
      <input type="text" id="firstName" v-model="firstName"><br><br>

      <label for="birthDay">Date de naissance :</label>
      <input type="text" id="birthDay" v-model="birthDay"><br><br>

      <label for="gender">Sexe :</label>
      <input type="text" id="gender" v-model="gender"><br><br>

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

<script>
import PatientInformationService from "@/services/PatInformationService.js";
import PatInformation from "@/model/PatInformation.js";

export default {
  name: "PatientResearch",

  data() {
    return {
      lastName: "",
      firstName: "",
      birthDay: "",
      gender: "",
      address: "",
      phone: "",
      patientList: [], // Liste des patients trouvés
      selectedPatient: new PatInformation()  // Patient sélectionné
    };
  },

  methods: {
    async searchPatient() {
      try {
        const filters = {
          lastName: this.lastName,
          firstName: this.firstName,
          birthDay: this.birthDay,
          gender: this.gender,
          address: this.address,
          phone: this.phone
        };

        this.patientList = await PatientInformationService.getPatInformationByAllInformation(this.lastName,this.firstName,this.birthDay,this.gender,this.address,this.phone);
      } catch (error) {
        console.error("Erreur lors de la recherche des patients:", error);
      }
    },

    updateSelectedPatient(patient) {
      if (patient) {
        this.selectedPatient = new PatInformation(
            patient.id,
            patient.lastName,
            patient.firstName,
            patient.birthDay,
            patient.gender,
            patient.address,
            patient.phone
        );
        console.log('Patient sélectionné:', this.selectedPatient);
      } else {
        this.selectedPatient = new PatInformation();
        console.log('Aucun patient sélectionné');
      }
    },

    goToPatientPage(patient) {
      this.$router.push({
        name: 'PatientPage',
        params: { id: patient.id },
        query: { data: JSON.stringify(patient) }
      });
    }
  }
};
</script>

<style scoped>
.body {
  background-color: greenyellow;
}
</style>
