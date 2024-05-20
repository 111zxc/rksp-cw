package coursework.Gateway.request;

import lombok.Getter;

@Getter
public class ProductRequest {
    private String name;
    private String description;
    private int price;
    private long sellerId;
    private String photoUrl;
}
