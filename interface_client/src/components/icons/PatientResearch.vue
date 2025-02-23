<template>
  <div class="body">
    <h2>Recherche patient</h2>
    <form action="#" method="post">
      <label for="lastName">Nom :</label>
      <input type="text" id="lastName" name="LastName" required><br><br>

      <label for="firstName">Prénom :</label>
      <input type="text" id="firstName" name="firstName" required><br><br>

      <label for="birthDay">Date de naissance :</label>
      <input type="text" id="birthDay" name="birthDay" required><br><br>

      <label for="gender">Sexe :</label>
      <input type="text" id="gender" name="gender"><br><br>

      <label for="address">Adresse :</label>
      <input type="text" id="address" name="address"><br><br>

      <label for="phone">Telephone :</label>
      <input type="text" id="phone" name="phone"><br><br>

      <input type="submit" value="Envoyer">
    </form>
    <div class="container">
      <h2 class="text-center">Résultat</h2>
      <table class="table table-striped">
        <thead>
        <tr>
          <th>lastName</th>
          <th>firstName</th>
          <th>birthDay</th>
          <th>gender</th>
          <th>address</th>
          <th>phone</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for ="patientInformation in patientList" v-bind:key="patientInformation.lastName">
          <td>{{ patientInformation.lastName }}</td>
          <td>{{ patientInformation.firstName }}</td>
          <td>{{ patientInformation.birthDay }}</td>
          <td>{{ patientInformation.gender }}</td>
          <td>{{ patientInformation.address }}</td>
          <td>{{ patientInformation.phone }}</td>
          <td><input type="checkbox" name="selectedPatients" :value="patientInformation"></td>
        </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<script>
import PatientInformationService from '../../services/PatientInformationService.js'
import PatientInformation from "@/model/PatientInformation.js";
export default {
  name: "PatientResearch",

  lastName: "",
  firstName: "",
  birthDay: "",
  gender: "",
  address: "",
  phone: "",
  patientInformation: PatientInformation = this.patientInformation,

  data(){
    return {
      patientList : []
    }
  },

  methods: {
    getAllPatient() {
      PatientInformationService.getAllPatientInformation().then((response) => {
        this.patientList = response.data();
      });
    }
  },
  async created() {
    this.patientList = await PatientInformationService.getAllPatientInformation()
  }
}

</script>

<style scoped>
.body{
  background-color: greenyellow;
}
</style>