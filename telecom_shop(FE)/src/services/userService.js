import axios from "axios";

const API_URL = "http://localhost:8080/user";

export const registerUser = async (userData) => {
    const response = await axios.post(
        `${API_URL}/create-account`,
        userData
    );

    return response.data;
};

export const loginUser = async (loginData) => {
    const response = await axios.post(
        `${API_URL}/login`,
        loginData,
        {withCredentials: true}
    );

    return response.data;
};

// Lấy thông tin người dùng hiện tại
export const getProfile = async () => { 
    const response = await axios.get( 
        `${API_URL}/profile`, 
        { withCredentials: true } 
    ); 
    return response.data; 
};

// ĐĂNG XUẤT 
export const logoutUser = async () => { 
    const response = await axios.post( 
        `${API_URL}/logout`, 
        {}, 
        { withCredentials: true } 
    ); 
    return response.data; 
};