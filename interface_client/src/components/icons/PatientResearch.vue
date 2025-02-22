<template>
  <div class="body">
    <h2>Recherche patient</h2>
    <div class="container">
      <h1 class="text-center"> Patient List</h1>
      <table class="table table-striped">
        <thead>
        <tr>
          <th>ID</th>
          <th>lastName</th>
          <th>firstName</th>
          <th>birthDay</th>
          <th>gender</th>
          <th>address</th>
          <th>phone</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for ="patientInformation in patientList" v-bind:key="patientInformation.id">
          <td>{{ patientInformation.id }}</td>
          <td>{{ patientInformation.lastName }}</td>
          <td>{{ patientInformation.firstName }}</td>
          <td>{{ patientInformation.birthDay }}</td>
          <td>{{ patientInformation.gender }}</td>
          <td>{{ patientInformation.address }}</td>
          <td>{{ patientInformation.phone }}</td>
        </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<script>
import PatientInformationService from '../../services/PatientInformationService.js'
export default {
  name: "PatientResearch",

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