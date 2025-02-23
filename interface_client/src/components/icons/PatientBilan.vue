<script >
import PatientInformationService from '../../services/PatientInformationService.js'
import PatientInformation from "@/model/PatientInformation.js";
export default {
  name: "PatientBilan",

  lastName: "",
  firstName: "",
  birthDay: "",
  gender: "",
  address: "",
  phone: "",
  patientInformation: PatientInformation = this.patientInformation,

  data(){
    return {
      patientList : []
    }
  },

  methods: {
    getAllPatient() {
      PatientInformationService.getAllPatientInformation().then((response) => {
        this.patientList = response.data();
      });
    }
  },
  async created() {
    this.patientList = await PatientInformationService.getAllPatientInformation()
  }
}
</script>

<template>
  <H1>Bilan</H1>
  <div>Numéro patient</div>
  <div>Bouton lancer bilan</div>

  <div>Resultat</div>
</template>

<style scoped>

</style>