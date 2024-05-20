package coursework.Gateway.service;

import coursework.Gateway.request.UserRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> createUser(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity("http://userservice:8080/api/users", requestEntity, String.class);
    }

    public ResponseEntity<String> getAllUsers() {
        String url = "http://userservice:8080/api/users";
        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<UserRequest> getUserById(long id) {
        String url = "http://userservice:8080/api/users/" + id;
        return restTemplate.getForEntity(url, UserRequest.class);
    }

    public ResponseEntity<String> updateUserById(long id, String username, String password, String role) {
        String url = "http://userservice:8080/api/users/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"role\": \"%s\"}",
                username, password, role);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    public ResponseEntity<String> deleteUserById(long id) {
        String url = "http://userservice:8080/api/users/" + id;
        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    public ResponseEntity<String> login(String username, String password) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            return restTemplate.postForEntity("http://userservice:8080/api/users/login", requestEntity, String.class);
        }
        catch (Exception exception) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<UserRequest> findByUsername (String username){
        String url = "http://userservice:8080/api/users/username/" + username;
        return restTemplate.getForEntity(url, UserRequest.class);
    }
}
