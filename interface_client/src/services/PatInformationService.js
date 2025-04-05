import axios from 'axios';
import PatInformation from "@/model/patient-information/PatInformation.js";
import userService from "@/services/UserService.js";


const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8083/pat-information'

const token = userService.getToken();
class PatInformationService {

    async getPatInformationByPatId(patId){
        try {
            const response = await axios.get(`${PATIENT_INFORMATION_API_BASE_URL}/patId/${patId}`, {
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                }
            });
            return response.data;
        } catch (error) {
            console.error('Erreur lors de la récupération des informations des utilisateurs:', error);
            throw error;
        }
    }

    async getPatInformationByAllInformation(patId, lastName, firstName, birthDay, gender, address, phone){
        try {
            const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL +'/information/', {
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
            console.error('Erreur lors de la récupération des utilisateurs:',  error.response?.status, error.response?.data);
            throw error;
        }
    }

    async addPatInformation(patInformation) {
        try {
            const response = await axios.post(PATIENT_INFORMATION_API_BASE_URL + '/add/', patInformation, {
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                }
            });
            return response.data;
        } catch (error){
            console.error("Erreur lors de l'ajout d'un nouveau patient", error);
            throw error;
        }
    }

    async updatePatInformation(patId, patInformation) {
        try {
            const response = await axios.put(PATIENT_INFORMATION_API_BASE_URL + '/update/' + patId, patInformation, {
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                }
            });
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

    async deletePatientInformation(patId, token) {
        try {
           const response = await axios.delete(PATIENT_INFORMATION_API_BASE_URL + '/delete/' +patId, {
               withCredentials: true,
               headers: {
                   "Authorization" : "Bearer " + token
               }
           });
           return response.status;
        } catch (error) {
            console.error("erreur lors de la suppression du patient", error)
            throw error;
        }
    }
}

export default new PatInformationService()