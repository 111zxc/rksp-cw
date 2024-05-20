import React, { useEffect, useState } from 'react';
import apiService from '../services/apiService';
import ProductCard from './ProductCard';

const ProductList = () => {
  const [products, setProducts] = useState([]);
  
  useEffect(() => {
    apiService.getAllProducts()
      .then(response => setProducts(response.data))
      .catch(error => console.error('Error fetching products:', error));
  }, []);

  return (
    <div className="product-list">
      <h2 className="product-list-title">Товары</h2>
      <div className="product-list-container">
        {products.map(product => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
};

export default ProductList;
