package coursework.AuthService.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import coursework.AuthService.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateToken(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        boolean isValid = jwtService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/revoke")
    public ResponseEntity<?> revoke(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        jwtService.revokeToken(token);
        return ResponseEntity.ok("Token revoked");
    }

    @PostMapping("/decode")
    public ResponseEntity<?> decode(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        DecodedJWT decodedJWT = jwtService.decodeToken(token);
        if (decodedJWT == null) {
            return ResponseEntity.status(400).body("Invalid token");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("issuer", decodedJWT.getIssuer());
        response.put("subject", decodedJWT.getSubject());
        response.put("expiresAt", decodedJWT.getExpiresAt());
        response.put("issuedAt", decodedJWT.getIssuedAt());
        response.put("claims", decodedJWT.getClaims());

        return ResponseEntity.ok(response);
    }
}
