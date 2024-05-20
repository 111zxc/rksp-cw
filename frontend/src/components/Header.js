import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';

const Header = () => {
  const { logout, isAuthenticated } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="header">
      <div className="header-container">
        <h1 className="header-logo">Мебель</h1>
        <nav className="header-nav">
          {isAuthenticated ? (
            <>
              <Link to="/" className="header-link">Главная</Link>
              <Link to="/create-product" className="header-link">Выложить товар</Link>
              <button onClick={handleLogout} className="header-button">Выйти</button>
            </>
          ) : (
            <Link to="/login" className="header-link">Войти</Link>
          )}
        </nav>
      </div>
    </header>
  );
};

export default Header;
