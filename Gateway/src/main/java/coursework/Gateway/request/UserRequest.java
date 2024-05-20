package coursework.Gateway.request;


import lombok.Getter;

@Getter
public class UserRequest {
    private long id;
    private String username;
    private String password;
    private String role;
}
