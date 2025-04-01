import axios from 'axios';
import PatInformation from "@/model/PatInformation.js";


const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8083/pat-information/'

const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWlsQG1haWwuZnIiLCJpYXQiOjE3NDM0NjcwNTgsImV4cCI6MTc0MzQ3MDY1OH0.GGwJEnmDWSZx38fwaXizlnBasSr54dI886ZGxjm-sOU";

const options = {
    hostname: 'localhost',
    port: 8083,
    path: 'pat-information/', // L'URL de l'API
    method: 'GET',
    headers: {
        'X-User-role': 'admin' // Ajouter le header X-User-role avec la valeur "admin"
    }
};

class PatInformationService {

    async getPatInformationByPatId(patId){
        try {
            const response = await axios.get(`${PATIENT_INFORMATION_API_BASE_URL}patId/${patId}`);
            return response.data;
        } catch (error) {
            console.error('Erreur lors de la récupération des informations des utilisateurs:', error);
            throw error;
        }
    }




    async getPatInformationByAllInformation(patId, lastName, firstName, birthDay, gender, address, phone){
        try {
            const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL +'information', {
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                },
                params: {
                    lastName: lastName,
                    firstName: firstName,
                    birthDay: birthDay,
                    address: address,
                    gender: gender,
                    phone: phone,
                    patId: patId
                }
            })
            return response.data.map(patientInformation => new PatInformation(
                patientInformation.patId,
                patientInformation.lastName,
                patientInformation.firstName,
                patientInformation.birthDay,
                patientInformation.gender,
                patientInformation.address,
                patientInformation.phone));
        } catch (error) {
            console.error('Erreur lors de la récupération des utilisateurs:', error.response?.status);
            throw error;
        }
    }

    async addPatInformation(patInformation) {
        try {
            const response = await axios.post(PATIENT_INFORMATION_API_BASE_URL + 'add/', patInformation);
            return response.data;
        } catch (error){
            console.error("Erreur lors de l'ajout d'un nouveau patient", error);
            throw error;
        }
    }

    async updatePatInformation(patId, patInformation) {
        try {
            const response = await axios.put(PATIENT_INFORMATION_API_BASE_URL + 'update/' + patId, patInformation);
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

export default new PatInformationService()