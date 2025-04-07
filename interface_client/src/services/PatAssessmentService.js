import axios from "axios";
import PatInformation from "@/model/patient-information/PatInformation.js";
import PatAssessment from "@/model/PatAssessment.js";
import userService from "@/services/UserService.js";

const PATIENT_ASSESSMENT_API_BASE_URL = 'http://localhost:8083/pat-assessment'


class PatAssessmentService {
    async getPatAssessment(requestAssessment){
        const token = userService.getToken()
        try {
            const response = await axios.post(PATIENT_ASSESSMENT_API_BASE_URL,requestAssessment, {
                withCredentials: true,
                headers: {
                    "Authorization" : "Bearer " + token
                }});
            return response.data
        } catch (error) {
            console.error('Service Erreur lors du chargement du bilan ', error);
            throw error;
        }
    }
}

export default new PatAssessmentService()