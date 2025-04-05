import axios from "axios";
import PatInformation from "@/model/patient-information/PatInformation.js";
import PatHistory from "@/model/PatHistory.js";
import userService from "@/services/UserService.js";
import PatHistories from "@/model/PatHistories.js";

const PATIENT_HISTORY_API_BASE_URL = 'http://localhost:8082/history'

const token = userService.getToken()

class PatientHistoryService {
    async getPatHistoryByPatId(patId) {
        try {
            const response = await axios.get(PATIENT_HISTORY_API_BASE_URL + '/patId/', {
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                },
                params: {patId: patId}
            });
            return response.data.map(patientHistories => new PatHistories(
                patientHistories.patId,
                patientHistories.patient,
                patientHistories.noteListHistories,
            ));
        } catch (error) {
            console.error('Erreur lors de la récupération des historiques des utilisateurs:', error);
            throw error;
        }
    }

    async addNote(patHistory) {
        try {
            const response = await axios.post(PATIENT_HISTORY_API_BASE_URL + '/note', patHistory, {
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                }
            });
            return response.data;
        } catch (error) {
            console.error('Erreur lors de l\'ajout de la note:', error);
            throw error;
        }
    }
}

export default new PatientHistoryService()