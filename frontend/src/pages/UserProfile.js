import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import apiService from '../services/apiService';

const UserProfile = () => {
  const { userId } = useParams();
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');
  const [message, setMessage] = useState('');

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await apiService.getUser(userId);
        setUser(response.data);
        setUsername(response.data.username);
        setPassword(response.data.password);
        setRole(response.data.role);
      } catch (error) {
        console.error('Error fetching user data:', error);
        setMessage('User not found');
      }
    };

    fetchUserData();
  }, [userId]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await apiService.updateUser(userId, { username, password, role });
      setMessage('Update successful');
    } catch (error) {
      console.error('Error updating user:', error);
      setMessage('Update failed');
    }
  };

  const handleDelete = async () => {
    try {
      await apiService.deleteUser(userId);
      setMessage('User deleted');
      navigate('/login');
    } catch (error) {
      console.error('Error deleting user:', error);
      setMessage('Deletion failed');
    }
  };

  if (!user) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="user-profile">
      <h2 className="user-profile-title">User Profile</h2>
      <form onSubmit={handleUpdate} className="user-profile-form">
        <div className="user-profile-input">
          <label>Username:</label>
          <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
        </div>
        <div className="user-profile-input">
          <label>Password:</label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </div>
        <div className="user-profile-input">
          <label>Role:</label>
          <input type="text" value={role} onChange={(e) => setRole(e.target.value)} required />
        </div>
        <button type="submit" className="user-profile-button">Update</button>
      </form>
      <button onClick={handleDelete} className="user-profile-button">Delete</button>
      {message && <p className="user-profile-message">{message}</p>}
    </div>
  );
};

export default UserProfile;
