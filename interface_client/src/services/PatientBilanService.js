import axios from "axios";
import PatientInformation from "@/model/PatientInformation.js";
import Bilan from "@/model/Bilan.js";

const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8083/assessment'

class PatientBilanService{
    async getPatientBilan(requestBilan){
        try {
            const response = await axios.post(PATIENT_INFORMATION_API_BASE_URL,requestBilan)
            return response.data
        } catch (error) {
            console.error('Service Erreur lors du chargement du bilan ', error);
            throw error;
        }
    }
}

export default new PatientBilanService()