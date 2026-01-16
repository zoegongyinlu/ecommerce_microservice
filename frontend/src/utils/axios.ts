import axios, { AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { message } from 'antd';

// Create axios instance
const apiClient: AxiosInstance = axios.create({
  baseURL: '/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor
apiClient.interceptors.request.use(
  (config) => {
    // Get token from localStorage
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    
    // Get user ID from localStorage
    const userId = localStorage.getItem('userId');
    if (userId) {
      config.headers['user-header'] = userId;
    }
    
    return config;
  },
  (error: AxiosError) => {
    console.error('Request error:', error);
    return Promise.reject(error);
  }
);

// Response interceptor
apiClient.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  (error: AxiosError) => {
    if (error.response) {
      const { status, data } = error.response;
      
      switch (status) {
        case 401:
          message.error('Authentication failed. Please login again.');
          localStorage.removeItem('token');
          localStorage.removeItem('userId');
          window.location.href = '/login';
          break;
        case 403:
          message.error('Access denied.');
          break;
        case 404:
          message.error('Resource not found.');
          break;
        case 500:
          message.error('Server error. Please try again later.');
          break;
        default:
          message.error((data as any)?.message || 'An error occurred.');
      }
    } else if (error.request) {
      message.error('Network error. Please check your connection.');
    } else {
      message.error('Request failed. Please try again.');
    }
    
    return Promise.reject(error);
  }
);

// Generic request function
export const request = async <T = any>(config: AxiosRequestConfig): Promise<T> => {
  try {
    const response = await apiClient.request<T>(config);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export default apiClient;
