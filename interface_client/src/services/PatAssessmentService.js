import axios from "axios";
import PatInformation from "@/model/patient-information/PatInformation.js";
import PatAssessment from "@/model/PatAssessment.js";
import userService from "@/services/UserService.js";

const PATIENT_ASSESSMENT_API_BASE_URL = '/pat-assessment'
const token = localStorage.getItem("jwt")

class PatAssessmentService {
    setToken(token) {
        this.token = token
    }

    async getPatAssessment(patId){
        try {
            const response = await axios.get(`${PATIENT_ASSESSMENT_API_BASE_URL}/patId/${patId}`, {
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