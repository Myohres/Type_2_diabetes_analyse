<template>
<h1>Patient Page</h1>
  <PatientResearch></PatientResearch>
  <PatientInformation></PatientInformation>
</template>

<script>
import PatientResearch from "@/components/icons/PatientResearch.vue";
import PatientInformation from "@/components/icons/PatientInformation.vue";
export default {
  name: "Patient",
  components: {PatientInformation, PatientResearch}
}
</script>

<style scoped>

</style>