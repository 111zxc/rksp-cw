package coursework.Gateway.controller;

import coursework.Gateway.request.RegisterRequest;
import coursework.Gateway.request.DecodedJWT;
import coursework.Gateway.request.UserRequest;
import coursework.Gateway.service.ChatService;
import coursework.Gateway.service.UserService;
import coursework.Gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final UserService userService;
    private final AuthService authService;
    private final ChatService chatService;


    @Autowired
    public ChatController(UserService userService, AuthService authService, ChatService chatService) {
        this.userService = userService;
        this.authService = authService;
        this.chatService = chatService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getMessages(@PathVariable long userId, @RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        if (Objects.isNull(token)) {return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!"); }
        if (authService.decodeToken(token).getStatusCode() == HttpStatus.OK){
            System.out.println("Token is ok!");
            String username = authService.decodeToken(token).getBody().getSubject();
            if (Objects.equals(username, userService.getUserById(userId).getBody().getUsername())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cant view messages with yourself!");
            }
            return chatService.getMessages(userId, userService.findByUsername(username).getBody().getId());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!");
    }

    @PostMapping("/send/{userId}")
    public ResponseEntity<String> sendMessage(@PathVariable long userId, @RequestBody String message, @RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        if (Objects.isNull(token)) {return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!"); }
        if (authService.decodeToken(token).getStatusCode() == HttpStatus.OK){
            System.out.println("Token is ok!");
            String username = authService.decodeToken(token).getBody().getSubject();
            if (Objects.equals(username, userService.getUserById(userId).getBody().getUsername())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cant send messages to yourself!");
            }
            return chatService.sendMessage(userService.findByUsername(username).getBody().getId(), userId, message);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!");
    }
}

