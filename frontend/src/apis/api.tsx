import axios from "axios";

export const SESSION_ATTRIBUTE_NAME = "JSESSIONID"

const loginApi = (username:string, password: string) => {
    axios.post('/login', {})
}

// const setUpAxiosInterceptors = (token: string) => {
//     axios.interceptors.request.use(
//         config => {
//             if (isUserLoggedIn()) {
//                 config.headers!!.authorization = token
//             }
//             return config;
//         }
//     )
// }

const isUserLoggedIn = ():boolean => {
    const user = sessionStorage.getItem(SESSION_ATTRIBUTE_NAME);
    if(user===null)
        return false;

    return true;
}
const logoutApi = () => {
    sessionStorage.removeItem(SESSION_ATTRIBUTE_NAME)
}