<template>
  <div class="body">
    <h1>Bilan Page</h1>
<WelcomeItem></WelcomeItem>
  </div>
</template>

<script>


import PatientResearch from "@/components/PatientResearch.vue";
import WelcomeItem from "@/components/WelcomeItem.vue";
export default {
  name: "Bilan",
  components: {WelcomeItem, PatientResearch}

}
</script>

<style scoped>

</style>