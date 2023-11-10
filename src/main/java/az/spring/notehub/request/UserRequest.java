package az.spring.notehub.request;

import az.spring.notehub.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    private String name;
    private String email;
    private String username;
    private String password;
    private UserRole userRole;

}