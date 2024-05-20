package coursework.Gateway.service;

import coursework.Gateway.request.ProductRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {
    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> createProduct(String name, String description, long sellerId, int price, String photoUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"name\": \"%s\", \"description\": \"%s\", \"sellerId\": %d, \"price\": %d, \"photoUrl\": \"%s\"}",
                name, description, sellerId, price, photoUrl);
        System.out.println(requestBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity("http://productservice:8081/api/products", requestEntity, String.class);
    }

    public ResponseEntity<String> getAllProducts() {
        String url = "http://productservice:8081/api/products";
        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<ProductRequest> getProductById(String id) {
        String url = "http://productservice:8081/api/products/" + id;
        return restTemplate.getForEntity(url, ProductRequest.class);
    }

    public ResponseEntity<String> updateProductById(String id, String name, String description, long sellerId, int price, String photoUrl) {
        String url = "http://productservice:8081/api/products/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = String.format("{\"name\": \"%s\", \"description\": \"%s\", \"sellerId\": %d, \"price\": %d, \"photoUrl\": \"%s\"}",
                name, description, sellerId, price, photoUrl);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    public ResponseEntity<String> deleteProductById(String id) {
        String url = "http://productservice:8081/api/products/" + id;
        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
