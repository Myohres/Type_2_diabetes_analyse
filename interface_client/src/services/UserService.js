import axios from "axios";

const PATIENT_INFORMATION_API_BASE_URL = 'http://localhost:8083/user'

class UserService{
    async getConnection(login, password){
        try {
            const response = await axios.get(PATIENT_INFORMATION_API_BASE_URL+'/connection/',{
                params : {
                    login : login,
                    password : password,
                }
            })
            return response.data
        } catch (error) {
            console.error('Service Erreur lors de la connection ', error);
            throw error;
        }
    }
}

export default new UserService()