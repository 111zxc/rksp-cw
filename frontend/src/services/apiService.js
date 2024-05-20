import axios from 'axios';

const apiService = {
  register: (data) => axios.post('http://localhost:8084/api/user/register', data),
  login: (data) => axios.post('http://localhost:8084/api/user/login', data, { responseType: 'text' }),
  getUser: (userId, token) => axios.get(`http://localhost:8084/api/user/${userId}`, {
    headers: { 'Authorization': token }
  }),
  updateUser: (userId, data, token) => axios.put(`http://localhost:8084/api/user/${userId}`, data, {
    headers: { 'Authorization': token }
  }),
  deleteUser: (userId, token) => axios.delete(`http://localhost:8084/api/user/${userId}`, {
    headers: { 'Authorization': token }
  }),
  getAllProducts: () => axios.get('http://localhost:8084/api/product'),
  getProductById: (productId) => axios.get(`http://localhost:8084/api/product/${productId}`),
  createProduct: (data, token) => axios.post('http://localhost:8084/api/product', data, {
    headers: { 'Authorization': token }
  }),
  updateProduct: (productId, data, token) => axios.put(`http://localhost:8084/api/product/${productId}`, data, {
    headers: { 'Authorization': token }
  }),
  deleteProduct: (productId, token) => axios.delete(`http://localhost:8084/api/product/${productId}`, {
    headers: { 'Authorization': token }
  }),
  getChats: (userId, token) => axios.get(`http://localhost:8084/api/chat/${userId}`, {
    headers: { 'Authorization': token }
  }),
  sendMessage: (userId, message, token) => axios.post(`http://localhost:8084/api/chat/send/${userId}`, message, {
    headers: { 'Authorization': token, 'Content-Type': 'text/plain' }
  })
};

export default apiService;
