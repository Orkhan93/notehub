package az.spring.notehub.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {

    private String name;
    private String username;
    private String email;
    private String password;

}