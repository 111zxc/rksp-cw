import React, { useState, useEffect, useContext, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import apiService from '../services/apiService';
import { AuthContext } from '../contexts/AuthContext';

const Chat = () => {
  const { userId } = useParams();
  const { token } = useContext(AuthContext);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [userCache, setUserCache] = useState({});

  const fetchUserName = useCallback(async (id) => {
    if (userCache[id]) {
      return userCache[id];
    }
    try {
      const response = await apiService.getUser(id, token);
      const username = response.data.username;
      setUserCache(prevCache => ({ ...prevCache, [id]: username }));
      return username;
    } catch (err) {
      console.error('Error fetching user:', err);
      return `Пользователь ${id}`;
    }
  }, [token, userCache]);

  const fetchMessages = useCallback(async () => {
    try {
      const response = await apiService.getChats(userId, token);
      const messagesWithUsernames = await Promise.all(response.data.map(async (msg) => {
        const senderName = await fetchUserName(msg.senderId);
        const receiverName = await fetchUserName(msg.receiverId);
        return { ...msg, senderName, receiverName };
      }));
      setMessages(messagesWithUsernames);
    } catch (err) {
      console.error('Error fetching messages:', err);
    }
  }, [userId, token, fetchUserName]);

  useEffect(() => {
    fetchMessages();
  }, [fetchMessages]);

  const handleSendMessage = async () => {
    try {
      await apiService.sendMessage(userId, newMessage, token);
      setNewMessage('');
      await fetchMessages();
    } catch (err) {
      console.error('Error sending message:', err);
    }
  };

  return (
    <div className="chat-container">
      <h2>Chat with {userCache[userId] || `Пользователь ${userId}`}</h2>
      <div>
        {messages.map((msg) => (
          <div key={msg.id} className="message-container">
            <p className="message-content">{msg.content}</p>
            <p className="message-sender">{msg.senderName}</p>
            <p className="message-timestamp">{msg.timestamp}</p>
          </div>
        ))}
      </div>
      <div className="message-input-container">
        <input 
          type="text" 
          value={newMessage} 
          onChange={(e) => setNewMessage(e.target.value)} 
          placeholder="Введите сообщение"
          className="message-input"
        />
        <button onClick={handleSendMessage} className="message-send-button">Отправить</button>
      </div>
    </div>
  );
};

export default Chat;
