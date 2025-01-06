import {getAuthToken} from './auth';

const API_URL = 'http://localhost:8080/api';

const createHeaders = () => {
    const headers = {
        'Content-Type': 'application/json',
    };

    const token = getAuthToken();
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    return headers;
};

export const api = {
    async get(endpoint) {
        const response = await fetch(`${API_URL}${endpoint}`, {
            headers: createHeaders(),
        });
        return response.json();
    },

    async post(endpoint, data) {
        const response = await fetch(`${API_URL}${endpoint}`, {
            method: 'POST',
            headers: createHeaders(),
            body: JSON.stringify(data),
        });
        return response.json();
    },

    async put(endpoint, data) {
        const response = await fetch(`${API_URL}${endpoint}`, {
            method: 'PUT',
            headers: createHeaders(),
            body: JSON.stringify(data),
        });
        return response.json();
    },

    async delete(endpoint) {
        const response = await fetch(`${API_URL}${endpoint}`, {
            method: 'DELETE',
            headers: createHeaders(),
        });
        return response.json();
    },
};