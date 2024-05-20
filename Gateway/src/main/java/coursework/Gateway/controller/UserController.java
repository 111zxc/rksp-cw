package coursework.Gateway.controller;

import coursework.Gateway.request.RegisterRequest;
import coursework.Gateway.request.DecodedJWT;
import coursework.Gateway.request.UserRequest;
import coursework.Gateway.service.UserService;
import coursework.Gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;


    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        return userService.createUser(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        ResponseEntity<String> result = userService.login(username, password);
        HttpStatus statusCode = (HttpStatus) result.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            return authService.generateToken(username);
        }
        return new ResponseEntity<>("Wrong login or password!", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRequest> getUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable long userId, @RequestBody UserRequest request, @RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        System.out.println(token);
        if (authService.decodeToken(token).getStatusCode() == HttpStatus.OK){
            System.out.println("Token is ok!");
            String username = Objects.requireNonNull(authService.decodeToken(token).getBody()).getSubject();
            UserRequest user = userService.findByUsername(username).getBody();
            assert user != null;
            System.out.println(user.getUsername() + username);
            if(Objects.equals(user.getUsername(), username) || Objects.equals(user.getRole(), "admin")){
                String password = request.getPassword();
                String role = request.getRole();
                String new_username = request.getUsername();
                userService.updateUserById(userId, new_username, password, role);
                return new ResponseEntity<>("User updated!", HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId, @RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        System.out.println(token);
        if (authService.decodeToken(token).getStatusCode() == HttpStatus.OK){
            System.out.println("Token is ok!");
            String username = Objects.requireNonNull(authService.decodeToken(token).getBody()).getSubject();
            UserRequest user = userService.findByUsername(username).getBody();
            assert user != null;
            System.out.println(user.getUsername() + username);
            if(Objects.equals(user.getRole(), "admin")){
                userService.deleteUserById(userId);
                return new ResponseEntity<>("User deleted!", HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!");
    }
}

