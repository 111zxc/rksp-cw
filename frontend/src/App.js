import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import UserProfile from './pages/UserProfile';
import Welcome from './pages/Welcome';
import ProductDetail from './pages/ProductDetail';
import CreateProduct from './pages/CreateProduct';
import Chat from './pages/Chat';
import Header from './components/Header';
import PrivateRoute from './components/PrivateRoute';
import { AuthProvider } from './contexts/AuthContext';

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Header />
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route 
            path="/user/:userId" 
            element={
              <PrivateRoute>
                <UserProfile />
              </PrivateRoute>
            } 
          />
          <Route 
            path="/product/:productId" 
            element={
              <PrivateRoute>
                <ProductDetail />
              </PrivateRoute>
            } 
          />
          <Route 
            path="/create-product" 
            element={
              <PrivateRoute>
                <CreateProduct />
              </PrivateRoute>
            } 
          />
          <Route 
            path="/chat/:userId" 
            element={
              <PrivateRoute>
                <Chat />
              </PrivateRoute>
            } 
          />
          <Route 
            path="/" 
            element={
              <PrivateRoute>
                <Welcome />
              </PrivateRoute>
            } 
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
};

export default App;
