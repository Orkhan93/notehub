package az.spring.notehub.response;

import az.spring.notehub.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {

    private String name;
    private String email;
    private String username;
    private String password;
    private UserRole userRole;

}