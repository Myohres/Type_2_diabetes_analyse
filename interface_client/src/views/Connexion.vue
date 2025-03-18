<template>
  <h1>Connexion</h1>
  <div>
    <div class="form-container">
      <div class="input-group">
        <input v-model="login" type="text" placeholder="login">
        <div class="error" :class="{ visible: errors.login }">{{ errors.login || " " }}</div>
      </div>

      <div class="input-group">
        <input v-model="password" type="text" placeholder="Mot de passe">
        <div class="error" :class="{ visible: errors.password }">{{ errors.password || " " }}</div>
      </div>
      <button @click="getConnection">Connexion</button>
    </div>
    <div class="message">{{ message }}</div>
  </div>
</template>


<script setup>
import UserService from "@/services/UserService.js";
import router from "@/router/index.js";
import {reactive, ref} from "vue";


const login = ref('');
const password = ref('');
const message = ref('');
const errors = reactive({
  login: "",
  password: "",
});


const getConnection = async () => {
  message.value = "";
  errors.login = login.value ? "" : "Le login est obligatoire.";
  errors.password = password.value ? "" : "Le mot de passe est obligatoire.";


  try {
    const response = await UserService.getConnection(login.value, password.value);
    if (response === true) {
      message.value = "Authentification réussie"
      await router.push({
        name: 'recherche',
      })
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      message.value = "Login ou mot de passe incorrect";
    } else {
      message.value = "Une erreur est survenue lors de la connexion.";
    }
  }
}

</script>

<style scoped>

</style>