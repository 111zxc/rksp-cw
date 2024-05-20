package coursework.ChatService.controller;

import coursework.ChatService.service.ChatService;
import coursework.ChatService.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public List<Message> getMessages(@RequestParam Long userId1, @RequestParam Long userId2) {
        return chatService.getMessages(userId1, userId2);
    }

    @PostMapping
    public Message sendMessage(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam String content) {
        return chatService.sendMessage(senderId, receiverId, content);
    }
}
