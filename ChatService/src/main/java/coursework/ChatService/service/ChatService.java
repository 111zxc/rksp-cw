package coursework.ChatService.service;

import coursework.ChatService.model.Message;
import coursework.ChatService.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessages(Long userId1, Long userId2) {
        List<Message> messages = messageRepository.findBySenderIdAndReceiverId(userId1, userId2);
        messages.addAll(messageRepository.findByReceiverIdAndSenderId(userId1, userId2));
        messages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));
        return messages;
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }
}

