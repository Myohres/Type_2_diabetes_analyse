import axios from "axios";
import PatInformation from "@/model/PatInformation.js";
import PatAssessment from "@/model/PatAssessment.js";

const PATIENT_ASSESSMENT_API_BASE_URL = 'http://localhost:8083/assessment'

class PatAssessmentService {
    async getPatAssessment(requestAssessment){
        try {
            const response = await axios.post(PATIENT_ASSESSMENT_API_BASE_URL,requestAssessment)
            return response.data
        } catch (error) {
            console.error('Service Erreur lors du chargement du bilan ', error);
            throw error;
        }
    }
}

export default new PatAssessmentService()