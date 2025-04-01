import axios from "axios";

const PATIENT_USER_API_BASE_URL = 'http://localhost:8083/user'
const PATIENT_AUTH_API_BASE_URL = 'http://localhost:8083/auth'

let token_session = "";

class UserService{
    async getConnection(login, password){
        try {
            const response = await axios.get(PATIENT_USER_API_BASE_URL+'/connexion/',{
                params : {
                    login : login,
                    password : password,
                }
            })
            return response.data
        } catch (error) {
            if (error.response && error.response.status === 404) {
                console.error('Utilisateur non trouvé (404)');
            } else {
                console.error('Service Erreur lors de la connexion', error);
            }
            throw error;
        }
    }

    async authentification(login, password) {
        try {
            const response = await axios.post(PATIENT_AUTH_API_BASE_URL+'/login', {
                params : {
                    login : login,
                    password : password,
                }
            })
            token_session = response.data;
            return response.data
        } catch (error) {
            console.error("Service erreur lors de l'authentification", error);
            throw error;
        }
    }

    async addUser(user){
        try {
            const response = await axios.post(PATIENT_USER_API_BASE_URL, user)
            return response.data;
        } catch (error) {
            console.error('Service addUser error : ', error);
            throw error;
        }
    }

    async loginFree(login){
        try {
            const response = await axios.get(`${PATIENT_USER_API_BASE_URL}/signUpLogin/`, {
                params : {
                    login : login,
                }
            });
            return response.data;
        } catch (error) {
            console.error('Service loginFree error : ', error);
            throw error;
        }
    }

    async getLogin(login){
        try {
            const response = await axios.post(`${PATIENT_USER_API_BASE_URL}/login/`)
            return response.data;
        } catch (error) {
            console.error('Service getLogin error : ', error);
            throw error;
        }
    }

    getToken(){
        return token_session;
}

}

export default new UserService()