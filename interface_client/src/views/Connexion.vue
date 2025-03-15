<template>
  <h1>Connexion</h1>
  <div>
    <input type="text" id="login" placeholder="Login"><br><br>
    <input type="password" id="password" placeholder="Mot de passe"><br><br>
    <button @click="getConnection">Connexion</button>
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
  </div>
</template>


<script setup>
import LoginPassword from "@/model/LoginPassword.js";
import UserService from "@/services/UserService.js";
import router from "@/router/index.js";
import {reactive, ref} from "vue";


const login = ref('');
const password = ref('');
const errors = reactive({
  login: "",
  password: "",
});


const getConnection = async () => {
  const login = document.getElementById("login").value;
  const password = document.getElementById("password").value;

  try {
    const response = await UserService.getConnection(login, password);
    if (response === true) {
      await router.push({
        name: 'recherche',
      })
    }
  } catch (error) {
    console.error("page erreur lors de la connection")
  }
}

</script>

<style scoped>

</style>