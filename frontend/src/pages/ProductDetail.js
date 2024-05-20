import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import apiService from '../services/apiService';
import { AuthContext } from '../contexts/AuthContext';

const ProductDetail = () => {
  const { productId } = useParams();
  const { token } = useContext(AuthContext);
  const [product, setProduct] = useState(null);
  const [seller, setSeller] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    photoUrl: '',
    price: 0
  });
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await apiService.getProductById(productId);
        setProduct(response.data);
        setFormData({
          name: response.data.name,
          description: response.data.description,
          photoUrl: response.data.photoUrl,
          price: response.data.price
        });
        const sellerResponse = await apiService.getUser(response.data.sellerId, token);
        setSeller(sellerResponse.data);
      } catch (err) {
        console.error('Error fetching product:', err);
      }
    };

    fetchProduct();
  }, [productId, token]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleUpdateProduct = async () => {
    try {
      await apiService.updateProduct(productId, { ...formData, sellerId: product.sellerId }, token);
      setEditMode(false);
      setProduct({ ...product, ...formData });
    } catch (err) {
      console.error('Error updating product:', err);
      alert('Error updating product');
    }
  };

  const handleDeleteProduct = async () => {
    try {
      await apiService.deleteProduct(productId, token);
      navigate('/');
    } catch (err) {
      console.error('Error deleting product:', err);
      alert('Error deleting product');
    }
  };

  return (
    <div className="product-detail-container">
      {product && (
        <div>
          <div className="product-detail-header">
            <h2 className="product-detail-title">{product.name}</h2>
            <div className="product-detail-buttons">
              <button onClick={() => setEditMode(true)} className="product-detail-button">Редактировать</button>
              <button onClick={handleDeleteProduct} className="product-detail-button">Удалить</button>
            </div>
          </div>
          <div className="product-detail-content">
            <div>
              <label className="product-detail-label">Описание:</label>
              <p>{product.description}</p>
            </div>
            <div>
              <label className="product-detail-label">Цена:</label>
              <p>{product.price}P</p>
            </div>
            <div>
              <label className="product-detail-label">Фото:</label>
              <img src={product.photoUrl} alt={product.name} className="product-detail-image" />
            </div>
            {seller && (
              <div>
                <label className="product-detail-label">Продавец:</label>
                <p>{seller.username}</p>
              </div>
            )}
            {seller && (
              <button onClick={() => navigate(`/chat/${seller.id}`)} className="product-detail-button">Пообщаться с продавцом</button>
            )}
          </div>
        </div>
      )}
      {editMode && (
        <div>
          <h2>Редактировать товар</h2>
          <form>
            <div>
              <label className="product-detail-label">Название:</label>
              <input type="text" name="name" value={formData.name} onChange={handleInputChange} className="product-detail-input" />
            </div>
            <div>
              <label className="product-detail-label">Описание:</label>
              <input type="text" name="description" value={formData.description} onChange={handleInputChange} className="product-detail-input" />
            </div>
            <div>
              <label className="product-detail-label">Ссылка на фото:</label>
              <input type="text" name="photoUrl" value={formData.photoUrl} onChange={handleInputChange} className="product-detail-input" />
            </div>
            <div>
              <label className="product-detail-label">Цена:</label>
              <input type="number" name="price" value={formData.price} onChange={handleInputChange} className="product-detail-input" />
            </div>
            <button type="button" onClick={handleUpdateProduct} className="product-detail-button">Обновить</button>
            <button type="button" onClick={() => setEditMode(false)} className="product-detail-button">Отменить</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default ProductDetail;
