import React, { useState } from 'react';
import apiService from '../services/apiService';

const Register = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await apiService.register({ username, password });
      setMessage('Успешная регистрация!');
    } catch (error) {
      setMessage('Registration failed!');
    }
  };

  return (
    <div className="register">
      <h2 className="register-title">Регистрация</h2>
      <form onSubmit={handleRegister} className="register-form">
        <div className="register-input">
          <label>Имя пользователя:</label>
          <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
        </div>
        <div className="register-input">
          <label>Пароль:</label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </div>
        <button type="submit" className="register-button">Зарегистрироваться</button>
      </form>
      {message && <p className="register-message">{message}</p>}
    </div>
  );
};

export default Register;
