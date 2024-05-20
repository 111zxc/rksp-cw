package coursework.Gateway.controller;

import coursework.Gateway.request.ProductRequest;
import coursework.Gateway.request.RegisterRequest;
import coursework.Gateway.request.DecodedJWT;
import coursework.Gateway.request.UserRequest;
import coursework.Gateway.service.ChatService;
import coursework.Gateway.service.ProductService;
import coursework.Gateway.service.UserService;
import coursework.Gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final UserService userService;
    private final AuthService authService;
    private final ProductService productService;

    @Autowired
    public ProductController(UserService userService, AuthService authService, ProductService productService) {
        this.userService = userService;
        this.authService = authService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<String> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductRequest> getProductById(@PathVariable String productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest product, @RequestHeader HttpHeaders headers){
        String token = headers.getFirst("Authorization");
        if (Objects.isNull(token)) {return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!"); }
        if (authService.decodeToken(token).getStatusCode() == HttpStatus.OK){
            System.out.println("Token is ok!");
            String name = product.getName();
            int price = product.getPrice();
            String description = product.getDescription();
            String photoUrl = product.getPhotoUrl();
            long sellerId = userService.findByUsername(authService.decodeToken(token).getBody().getSubject()).getBody().getId();
            productService.createProduct(name, description, sellerId, price, photoUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!");
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable String productId, @RequestBody ProductRequest product, @RequestHeader HttpHeaders headers){
        String token = headers.getFirst("Authorization");
        if (Objects.isNull(token)) {return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!"); }
        if (authService.decodeToken(token).getStatusCode() == HttpStatus.OK){
            System.out.println("Token is ok!");
            long seller_id = productService.getProductById(productId).getBody().getSellerId();
            System.out.println("seller_id: " + seller_id);
            long seller_id2 = userService.findByUsername(authService.decodeToken(token).getBody().getSubject()).getBody().getId();
            System.out.println("seller_id2: " + seller_id2);
            if (seller_id == seller_id2) {
                System.out.println("SOVPALI");
                String name = product.getName();
                int price = product.getPrice();
                String description = product.getDescription();
                String photoUrl = product.getPhotoUrl();
                productService.updateProductById(productId, name, description, seller_id2, price, photoUrl);
                return ResponseEntity.status(HttpStatus.OK).body("Product updated!");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId, @RequestHeader HttpHeaders headers){
        String token = headers.getFirst("Authorization");
        if (Objects.isNull(token)) {return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!"); }
        if (authService.decodeToken(token).getStatusCode() == HttpStatus.OK){
            System.out.println("Token is ok!");
            long seller_id = productService.getProductById(productId).getBody().getSellerId();
            System.out.println("seller_id: " + seller_id);
            long seller_id2 = userService.findByUsername(authService.decodeToken(token).getBody().getSubject()).getBody().getId();
            System.out.println("seller_id2: " + seller_id2);
            if (seller_id == seller_id2) {
                System.out.println("SOVPALI");
                productService.deleteProductById(productId);
                return ResponseEntity.status(HttpStatus.OK).body("Product deleted!");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not enough rights!");
    }
}

