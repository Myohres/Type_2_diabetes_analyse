<template>
<h1>Patient Page</h1>
  <PatientResearch></PatientResearch>
  <PatientInformation></PatientInformation>
  <patient-historique></patient-historique>

</template>

<script>

import PatientResearch from "@/components/icons/PatientResearch.vue";
import PatientInformation from "@/components/icons/PatientInformation.vue";
import PatientHistorique from "@/components/icons/PatientHistorique.vue";
import {ref} from "vue";
import {api} from "@/services/patientInformationService.js";



export default {
  name: "Patient",
  components: {PatientHistorique, PatientInformation, PatientResearch}
}
</script>

<style scoped>

</style>