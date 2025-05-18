

<template>
  <nav>
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/recherche">Recherche Patient</RouterLink>
    <RouterLink to="/nouveauPatient">Ajouter patient</RouterLink>
    <RouterLink to="/deconnexion">Déconnexion</RouterLink>
  </nav>
  <p>Souhaitez vous vous déconnecter ?</p>
  <button @click="deconnection">Deconnexion</button>
</template>

<script setup>
import {RouterLink} from "vue-router";
import router from "@/router/index.js";


const deconnection = async () => {
  localStorage.removeItem("jwt")
  await router.push('Connexion')
}


</script>

<style scoped>

</style>