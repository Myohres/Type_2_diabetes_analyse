import axios from "axios";
import PatientInformation from "@/model/PatientInformation.js";
import PatientHistorique from "@/model/PatientHistorique.js";

const PATIENT_HISTORIQUE_API_BASE_URL = 'http://localhost:8081/historique'

class PatientHistoriqueService {
    async getPatientHistoriqueByPatId(patId) {
        try {
            const response = await axios.get(PATIENT_HISTORIQUE_API_BASE_URL + '/patId', {
                params: {patId: patId}
            });
            return response.data.map(patientHistorique => new PatientHistorique(
                patientHistorique.id,
                patientHistorique.patId,
                patientHistorique.patient,
                patientHistorique.note,
            ));
        } catch (error) {
            console.error('Erreur lors de la récupération des utilisateurs:', error);
            throw error;
        }
    }

    async addNote(patientHistorique) {
        try {
            const response = await axios.post(PATIENT_HISTORIQUE_API_BASE_URL + '/note', patientHistorique);
            return response.data;
        } catch (error) {
            console.error('Erreur lors de l\'ajout de la note:', error);
            throw error;
        }
    }
}

export default new PatientHistoriqueService()