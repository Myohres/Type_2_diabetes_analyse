<template>
  <div class="body">
    <PatientResearch></PatientResearch>
    <PatientBilan></PatientBilan>

  </div>
</template>

<script>
import PatientResearch from "@/components/icons/PatientResearch.vue";
import PatientBilan from "@/components/icons/PatientBilan.vue";

export default {
  name: "Bilan",
  components: {PatientBilan, PatientResearch }

}
</script>

<style scoped>

</style>