<template>
  <div class="body">
    <h2>Ajouter Patient</h2>
    <div class="form-container">
      <form @submit.prevent="searchPatient">

        <div class="input-group">
          <input type="text" id="lastName" v-model="lastName" placeholder="Nom" required="required">
          <div class="error" :class="{ visible: errors.lastName }">{{ errors.lastName || " " }}</div>
        </div>

        <div class="input-group">
          <input type="text" id="firstName" v-model="firstName" placeholder="Prénom" required="required">
          <div class="error" :class="{ visible: errors.firstName }">{{ errors.firstName || " " }}</div>
        </div>

        <div class="input-group">
          <input type="date" id="birthDay" v-model="birthDay" required="required">
          <div class="error" :class="{ visible: errors.birthDay }">{{ errors.birthDay || " " }}</div>
        </div>

        <div class="input-group">
          <select id="gender" v-model="gender" required="required">
            <option value="">Sexe</option>
            <option value="M">Homme</option>
            <option value="F">Femme</option>
          </select>
          <div class="error" :class="{ visible: errors.gender }">{{ errors.gender || " " }}</div>
        </div>

        <div class="input-group">
          <input type="text" id="address" v-model="address" placeholder="Adresse">
          <div class="error" ></div>
        </div>

        <div class="input-group">
          <input type="text" id="phone" v-model="phone" placeholder="Téléphonne">
          <div class="error" ></div>
        </div>

        <button type="button" @click="addPatient">Ajouter patient</button>
        <div class="message">{{ message }}</div>
      </form>
    </div>


  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import { useRouter } from "vue-router";
import PatientInformationService from "@/services/PatientinformationService.js";
import UserService from "@/services/UserService.js";
import PatientInformation from "@/model/PatientInformation.js";
import PatientToAdd from "@/model/PatientToAdd.js";

const lastName = ref("");
const firstName = ref("");
const birthDay = ref("");
const gender = ref("");
const address = ref("");
const phone = ref("");
const message = ref('');
const errors = reactive({
  lastName: "",
  firstName: "",
  birthDay: "",
  gender: "",
  address: "",
  phone: "",
});

const addPatient = async () => {
  message.value = "";
  errors.lastName = lastName.value ? "" : "Le nom est obligatoire.";
  errors.firstName = firstName.value ? "" : "Le prénom est obligatoire.";
  errors.birthDay = birthDay.value ? "" : "La date de naissance est obligatoire";
  errors.gender = birthDay.value ? "" : "Le sexe est obligatoire";



  const newPatient = new PatientToAdd( lastName.value, firstName.value, birthDay.value, gender.value, address.value, phone.value)
  await PatientInformationService.addPatient(newPatient);
  message.value = "patient ajouté"


}
</script>

<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 300px;
  margin: auto;
}
</style>
