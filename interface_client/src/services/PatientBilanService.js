import axios from "axios";
import PatientInformation from "@/model/PatientInformation.js";
import Bilan from "@/model/Bilan.js";

const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8082/assessment'

class PatientBilanService{
    async getPatientBilan(patId, patientNoteList, birthDay, gender){
        const cleanPatientNoteList = patientNoteList.map(note => (typeof note === "object" ? JSON.stringify(note) : note));
        try {
            const response = await axios.post(PATIENT_INFORMATION_API_BASE_URL,{
                    patId: patId,
                    patientNoteList: cleanPatientNoteList,
                    birthDay: birthDay,
                    gender: gender
                })

            return response.data.map(bilan => new Bilan( bilan.id, bilan.riskLevel))
        } catch (error) {
            console.error('Service Erreur lors du chargement du bilan', error);
            throw error;
        }
    }
}

export default new PatientBilanService()