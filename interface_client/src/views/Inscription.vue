<template>
<h1>Inscription</h1>
  <div>
    <input id="lastName" placeholder="Nom">
    <input id="firstName" placeholder="Prénom">
    <input id="login" placeholder="login">
    <input id="password" placeholder="mot de passe">

    <button @click="inscription">Inscription</button>
  </div>
  <div>{{message}}</div>
</template>

<script setup>

import UserService from "@/services/UserService.js";
import User from "@/model/User.js";
let message = "";

const inscription = async () => {
  const login = document.getElementById("login").value;
  const firstName = document.getElementById("firstName").value;
  const lastName = document.getElementById("lastName").value;
  const password = document.getElementById("password").value;
  const user = new User(login, password, lastName, firstName);

  try {
     await UserService.addUser(user);
    message = "Inscription réussi";
  } catch (error) {
    console.error("erreur pendant l'inscription" +error);
  }
}
</script>

<style scoped>

</style>