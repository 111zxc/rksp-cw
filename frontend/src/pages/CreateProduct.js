import React, { useState, useContext } from 'react';
import apiService from '../services/apiService';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';

const CreateProduct = () => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [photoUrl, setPhotoUrl] = useState('');
  const [price, setPrice] = useState('');
  const [message, setMessage] = useState('');
  const { token } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        console.log({name, description, photoUrl, price, token })
      await apiService.createProduct({ name, description, photoUrl, price }, token);
      setMessage('Товар успешно выложен!');
      navigate('/');
    } catch (error) {
      setMessage('Error creating product!');
      console.error('Error creating product:', error);
    }
  };

  return (
    <div>
      <h2>Выложить товар</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Название</label>
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
        </div>
        <div>
          <label>Описание</label>
          <input type="text" value={description} onChange={(e) => setDescription(e.target.value)} required />
        </div>
        <div>
          <label>Ссылка на фотографию</label>
          <input type="text" value={photoUrl} onChange={(e) => setPhotoUrl(e.target.value)} required />
        </div>
        <div>
          <label>Цена</label>
          <input type="number" step="0.01" value={price} onChange={(e) => setPrice(e.target.value)} required />
        </div>
        <button type="submit">Выложить</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default CreateProduct;
