<template>
  <div class="body">
<h2>Recherche patient</h2>
    <div class="container">
      <h1 class="text-center"> Patient List</h1>
      <table class="table table-striped">
        <thead>
          <th>ID</th>
          <th>LastName</th>
          <th>FirstName</th>
          <th>BirthDay</th>
          <th>Gender</th>
          <th>Address</th>
          <th>Phone</th>
        </thead>
        <tbody>
          <tr v-for ="patient in patientList" v-bind:key="patient.id">
              <td>{{patient.id}}</td>
            <td>{{patient.lastName}}</td>
            <td>{{patient.firstName}}</td>
            <td>{{patient.birthDay}}</td>
            <td>{{patient.gender}}</td>
            <td>{{patient.address}}</td>
            <td>{{patient.phone}}</td>
          </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<script>
import PatientService from '../../services/patientInformationService.js'
export default {
  name: "PatientResearch",

  data(){
    return {
      patientList : []
    }
  },
  methods: {
    getAllPatient(){
      PatientInformationService.getAllPatient().then((response) =>{
        this.patientList = response.data();
      });
  },
  created() {
      this.getAllPatient()

  }
  }
}

</script>

<style scoped>
.body{
  background-color: greenyellow;
}
</style>