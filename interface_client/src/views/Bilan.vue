<template>
  <div class="body">
  <PatientResearch></PatientResearch>
  <H1>Bilan</H1>
  <div>Numéro patient</div>
  <div>Bouton lancer bilan</div>

  <div>Resultat</div>
  </div>
</template>

<script>
import PatientResearch from "@/components/icons/PatientResearch.vue";

export default {
  name: "Bilan",
  components: {PatientResearch }

}
</script>

<style scoped>

</style>