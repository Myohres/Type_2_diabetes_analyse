import axios from "axios";
import PatInformation from "@/model/patient-information/PatInformation.js";
import PatHistory from "@/model/patient-history/PatHistory.js";
import userService from "@/services/UserService.js";
import PatHistories from "@/model/patient-history/PatHistories.js";

const PATIENT_HISTORY_API_BASE_URL = '/pat-history'
const token = localStorage.getItem("jwt")

class PatientHistoryService {
    async getPatHistoryByPatId(patId) {
        try {
            const response = await axios.get(`${PATIENT_HISTORY_API_BASE_URL}/patId/${patId}`,{
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                }
            });
            return response.data.map(patHistories => new PatHistories(
                patHistories.patId,
                patHistories.patient,
                patHistories.noteListHistories)
            )

        } catch (error) {
            console.error('Erreur lors de la récupération des historiques des utilisateurs:', error);
            throw error;
        }
    }

    async getNoteListHistoriesByPatId(patId) {
        try {
            const response = await axios.get(`${PATIENT_HISTORY_API_BASE_URL}/noteListHistories/${patId}`,{
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                }
            });
            return response.data

        } catch (error) {
            console.error('Erreur lors de la récupération des historiques de utilisateur:', error);
            throw error;
        }
    }

    async addNote(patHistory) {
        try {
            const response = await axios.post(PATIENT_HISTORY_API_BASE_URL + '/add', patHistory, {
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