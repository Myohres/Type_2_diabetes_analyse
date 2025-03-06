import axios from "axios";
import PatientInformation from "@/model/PatientInformation.js";
import patientInformation from "@/components/PatientInformation.vue";

const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8080/patient/'


class PatientinformationService{
   async getAllPatientInformation(){
       try {
           const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL);
           return response.data.map(patientInformation => new PatientInformation(
               patientInformation.id,
               patientInformation.lastName,
               patientInformation.firstName,
               patientInformation.birthDay,
               patientInformation.gender,
               patientInformation.address,
               patientInformation.phone));
       } catch (error) {
           console.error('Erreur lors de la récupération des utilisateurs:', error);
           throw error;
       }

    }

    async getPatientByAllInformation(lastName, firstName, birthDay, gender, address, phone){
       try {
           const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL+'information/', {
               params: {
                   lastName: lastName,
                   firstName: firstName,
                   birthDay: birthDay,
                   gender: gender,
                   address: address,
                   phone: phone
               } });
           return response.data.map(patientInformation => new PatientInformation(
               patientInformation.id,
               patientInformation.lastName,
               patientInformation.firstName,
               patientInformation.birthDay,
               patientInformation.gender,
               patientInformation.address,
               patientInformation.phone));
       } catch (error) {
           console.error('Erreur lors de la récupération des utilisateurs:', error);
           throw error;
       }
    }


}

export default new PatientinformationService()