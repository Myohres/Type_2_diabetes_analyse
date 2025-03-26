<script >
import PatientInformationService from '../services/PatInformationService.js'
import PatInformation from "@/model/PatInformation.js";
export default {
  name: "PatientBilan",

  lastName: "",
  firstName: "",
  birthDay: "",
  gender: "",
  address: "",
  phone: "",
  patientInformation: PatInformation = this.patientInformation,

  data(){
    return {
      patientList : []
    }
  },

  methods: {
    getAllPatient() {
      PatientInformationService.getAllPatInformation().then((response) => {
        this.patientList = response.data();
      });
    }
  },
  async created() {
    this.patientList = await PatientInformationService.getAllPatInformation()
  }
}
</script>

<template>
  <H1>PatAssessment</H1>
  <div>Numéro patient</div>
  <div>Bouton lancer patAssessment</div>

  <div>Resultat</div>
</template>

<style scoped>

</style>