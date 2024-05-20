import React from 'react';
import { Link } from 'react-router-dom';

const ProductCard = ({ product }) => {
  return (
    <div className="product-card">
      <Link to={`/product/${product.id}`} className="product-link">
        <h3 className="product-name">{product.name}</h3>
        <img src={product.photoUrl} alt={product.name} className="product-image" />
      </Link>
      <p className="product-price">Цена: {product.price}₽</p>
    </div>
  );
};

export default ProductCard;
