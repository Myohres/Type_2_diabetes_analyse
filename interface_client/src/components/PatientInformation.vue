<template>
  <div class="body">
  <h2>Patient Information</h2>
    <div>
      <input placeholder="Nom">
      <input placeholder="Prénom">
      <input placeholder="Date de naissance">
      <input placeholder="Sexe">
      <input placeholder="Adresse">
      <input placeholder="Téléphone">
      <button>Modifier patient</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "PatientInformation"
}
</script>

<style scoped>
.body{
  background-color: green;
}
</style>