import axios from "axios";
import PatientInformation from "@/model/PatientInformation.js";


const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8080/patient/'


class PatientinformationService{
   async getAllPatientInformation(){
       try {
           const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL);
           return response.data.map(patientInformation => new PatientInformation(
               patientInformation.patId,
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

    async getPatientByAllInformation(patId, lastName, firstName, birthDay, gender, address, phone){
       try {
           const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL+'information/', {
               params: {
                   patId: patId,
                   lastName: lastName,
                   firstName: firstName,
                   birthDay: birthDay,
                   gender: gender,
                   address: address,
                   phone: phone
               } });
           return response.data.map(patientInformation => new PatientInformation(
               patientInformation.patId,
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

    async addPatient(newPatient) {
       try {
           const response = await axios.post(PATIENT_INFORMATION_API_BASE_URL + 'add/', newPatient);
           return response.data;
       } catch (error){
           console.error("Erreur lors de l'ajout d'un nouveau patient", error);
           throw error;
       }
    }

    async updatePatientInformation(patId, patientInformation) {
        try {
            const response = await axios.put(PATIENT_INFORMATION_API_BASE_URL + 'update/' + patId, {patientInformation});
            return response.data;
        } catch (error) {
            if (error.response && error.response.status === 400) {
                const errorMessages = error.response.data;
                errorMessages.forEach((msg) => {
                    console.error(msg);
                });
            } else {
                console.error("Erreur lors de la soumission du formulaire", error);
            }
        }
    }
}

export default new PatientinformationService()