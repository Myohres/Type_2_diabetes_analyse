import axios from "axios";
import PatInformation from "@/model/patient-information/PatInformation.js";
import PatHistory from "@/model/PatHistory.js";

const PATIENT_HISTORY_API_BASE_URL = 'http://localhost:8082/history'

class PatientHistoryService {
    async getPatHistoryByPatId(patId) {
        try {
            const response = await axios.get(PATIENT_HISTORY_API_BASE_URL + '/patId', {
                params: {patId: patId}
            });
            return response.data.map(patientHistorique => new PatHistory(
                patientHistorique.id,
                patientHistorique.patId,
                patientHistorique.patient,
                patientHistorique.note,
            ));
        } catch (error) {
            console.error('Erreur lors de la récupération des historiques des utilisateurs:', error);
            throw error;
        }
    }

    async addNote(patHistory) {
        try {
            const response = await axios.post(PATIENT_HISTORY_API_BASE_URL + '/note', patHistory);
            return response.data;
        } catch (error) {
            console.error('Erreur lors de l\'ajout de la note:', error);
            throw error;
        }
    }
}

export default new PatientHistoryService()