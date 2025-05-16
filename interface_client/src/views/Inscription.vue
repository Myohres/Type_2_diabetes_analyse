<template>
  <nav>
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/inscription">Inscription</RouterLink>
    <RouterLink to="/connexion">Connexion</RouterLink>

  </nav>
  <h1>Inscription</h1>
  <div class="form-container">
    <div class="input-group">
      <input v-model="lastName" type="text" placeholder="Nom">
      <div class="error" :class="{ visible: errors.lastName }">{{ errors.lastName || " " }}</div>
    </div>

    <div class="input-group">
      <input v-model="firstName" type="text" placeholder="Prénom">
      <div class="error" :class="{ visible: errors.firstName }">{{ errors.firstName || " " }}</div>
    </div>

    <div class="input-group">
      <input v-model="login" type="email" placeholder="Login">
      <div class="error" :class="{ visible: errors.login }">{{ errors.login || " " }}</div>
    </div>

    <div class="input-group">
      <input v-model="password" type="password" placeholder="Mot de passe">
      <div class="error" :class="{ visible: errors.password }">{{ errors.password || " " }}</div>
    </div>

    <div class="input-group">
      <input v-model="confirmPassword" type="password" placeholder="Confirmer le mot de passe">
      <div class="error" :class="{ visible: errors.confirmPassword }">{{ errors.confirmPassword || " " }}</div>
    </div>

    <button @click="inscription">Inscription</button>
  </div>
  <div class="message">{{ message }}</div>
</template>

<script setup>
import UserService from "@/services/UserService.js";
import User from "@/model/User.js";
import { ref, reactive } from 'vue';
import {RouterLink} from "vue-router";

const lastName = ref('');
const firstName = ref('');
const login = ref('');
const password = ref('');
const confirmPassword = ref('');
const message = ref('');
const errors = reactive({
  lastName: "",
  firstName: "",
  login: "",
  password: "",
  confirmPassword: ""
});

const inscription = async () => {
  message.value = "";
  errors.lastName = lastName.value ? "" : "Le nom est obligatoire.";
  errors.firstName = firstName.value ? "" : "Le prénom est obligatoire.";
  errors.login = login.value ? "" : "Le login est obligatoire.";
  errors.password = password.value ? "" : "Le mot de passe est obligatoire.";
  errors.confirmPassword = confirmPassword.value === password.value ? "" : "Les mots de passe doivent être identiques.";


  if (Object.values(errors).some(error => error)) {
    message.value = "Veuillez corriger les erreurs.";
    return;
  }

  try {
   /* const loginFree = await UserService.loginFree(login.value);
    if (loginFree === false) {
      message.value = "Login déjà pris";
      return;
    }*/

    const user = new User(login.value, password.value, lastName.value, firstName.value);
    await UserService.addUser(user);
    message.value = "Inscription réussie !";
  } catch (error) {
    console.error("Erreur pendant l'inscription :", error);
    message.value = "Une erreur s'est produite.";
  }
};
</script>

<style>
.form-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 300px;
  margin: auto;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.error {
  color: red;
  font-size: 0.9em;
  min-height: 18px;
  visibility: hidden;
}

.error.visible {
  visibility: visible;
}



.message {
  margin-top: 10px;
  font-weight: bold;
}
</style>
