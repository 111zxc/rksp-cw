package coursework.Gateway.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {
    private final RestTemplate restTemplate;

    public ChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getMessages(long userId1, long userId2) {
        String url = "http://chatservice:8082/api/chats?userId1={userId1}&userId2={userId2}";
        return restTemplate.getForEntity(url, String.class, userId1, userId2);
    }

    public ResponseEntity<String> sendMessage(long senderId, long receiverId, String message) {
        String fullUrl = String.format("http://chatservice:8082/api/chats?senderId=%d&receiverId=%d&content=%s", senderId, receiverId, message);
        return restTemplate.postForEntity(fullUrl, null, String.class);
    }
}

