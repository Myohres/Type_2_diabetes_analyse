import axios from "axios";
import PatientInformation from "@/model/PatientInformation.js";
import PatientHistorique from "@/model/PatientHistorique.js";

const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8080/historique'

class PatientHistoriqueService{
    async getPatientHistoriqueByPatId(patId){
        try {
            const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL+'/patId',{
                params: { patId: patId}});
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

}

export default new PatientHistoriqueService()