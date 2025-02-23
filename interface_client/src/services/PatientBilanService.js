import axios from "axios";
import PatientInformation from "@/model/PatientInformation.js";

const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8082/assessment/'

class PatientBilanService{
    async getPatientBilan(id, patientNoteList, birthDay, gender){
        try {
            const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL,);
            return response.data
        } catch (error) {
            console.error('Erreur lors de la récupération des utilisateurs:', error);
            throw error;
        }

    }
}

export default new PatientinformationService()