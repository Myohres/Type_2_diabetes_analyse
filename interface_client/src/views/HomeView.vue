<script setup>
import {RouterLink} from "vue-router";
</script>

<template>

  <main>

    <nav>
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/inscription">Inscription</RouterLink>
      <RouterLink to="/connexion">Connexion</RouterLink>
    </nav>
  </main>
</template>

<style>
main{
  border-bottom-width: 5px;
  border: #222222;

}
</style>
