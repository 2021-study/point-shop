import axios from "axios";
import {LoginRequest, Token} from "./interfaces";

export const COMMON_HEADER_ATTRIBUTE_AUTH = "Authorization"

async function loginApi(userId:string, password: string) : Promise<Token> {
    const response = await axios.post<Token>("/login",
        {userId:userId, password:password},)
        // .then((response)=>{
        //     setLocalStorage("JWT", response.data.token)
        //     setUpToken(response.data.token)
        // })

    return response.data;
}

const setUpToken = (token: string | null) => {
    if(token) {
        axios.defaults.headers.common[COMMON_HEADER_ATTRIBUTE_AUTH] = `Bearer ${token}`;
        console.log("axois.defaults.headers : ", axios.defaults.headers);
    } else {
        delete axios.defaults.headers.common[COMMON_HEADER_ATTRIBUTE_AUTH]
    }
}

const setLocalStorage = (key: string, value: string) => {
    localStorage.setItem(key, JSON.stringify(value));
}

const clearLocalStorageByKey = (key: string) => {
    localStorage.removeItem(key);
}


export {loginApi, setUpToken, setLocalStorage,clearLocalStorageByKey}
