package coursework.Gateway.service;

import coursework.Gateway.request.DecodedJWT;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> generateToken(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"username\": \"" + username + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity("http://authservice:8083/api/auth/generate", requestEntity, String.class);
    }

    public ResponseEntity<String> validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"token\": \"" + token + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity("http://authservice:8083/api/auth/validate", requestEntity, String.class);
    }

    public ResponseEntity<String> revokeToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"token\": \"" + token + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity("http://authservice:8083/api/auth/revoke", requestEntity, String.class);
    }

    public ResponseEntity<DecodedJWT> decodeToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"token\": \"" + token + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity("http://authservice:8083/api/auth/decode", requestEntity, DecodedJWT.class);
    }
}
