import React, { useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import apiService from '../services/apiService';
import { AuthContext } from '../contexts/AuthContext';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await apiService.login({ username, password });
      const token = response.data;
      login(token);
      navigate('/');
    } catch (err) {
      setError('Login failed!');
      console.error('Error logging in:', err);
    }
  };

  return (
    <div className="login-container">
      <h2 className="login-title">Авторизация</h2>
      <form onSubmit={handleSubmit}>
        <div className="input-container">
          <label>Имя пользователя</label>
          <input 
            type="text" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            required 
          />
        </div>
        <div className="input-container">
          <label>Пароль</label>
          <input 
            type="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
          />
        </div>
        <button type="submit" className="login-button">Войти</button>
        {error && <p className="error-message">{error}</p>}
      </form>
      <p>Ещё нет аккаунта? <Link to="/register">Зарегистрируйтесь!</Link></p>
    </div>
  );
};

export default Login;
