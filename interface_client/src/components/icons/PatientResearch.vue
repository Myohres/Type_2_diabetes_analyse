<template>
  <div class="body">
<h2>Recherche patient</h2>
  <div>
    <input placeholder="Nom">
    <input placeholder="Prénom">
    <input placeholder="Date de naissance">
    <input placeholder="Sexe">
    <input placeholder="Adresse">
    <input placeholder="Téléphone">
    <button>Rechercher patient</button>
  </div>
  <h2>Resultat</h2>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>LastName</th>
        <th>FirstName</th>
        <th>Date de naissance</th>
        <th>Sexe</th>
        <th>Adresse</th>
        <th>Téléphonne</th>
      </tr>
      </thead>
      <tr>
        <th>Id</th>
        <th>LastName</th>
        <th>FirstName</th>
        <th>Date de naissance</th>
        <th>Sexe</th>
        <th>Adresse</th>
        <th>Téléphonne</th>
        <input type="radio" v-model="picked">
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "PatientResearch"
}
</script>

<style scoped>
.body{
  background-color: greenyellow;
}
</style>