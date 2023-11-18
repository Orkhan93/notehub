package az.spring.notehub.request;

import az.spring.notehub.constraint.validation.Name;
import az.spring.notehub.constraint.validation.Password;
import az.spring.notehub.constraint.validation.Username;
import az.spring.notehub.enums.UserRole;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    @Name
    private String name;

    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Username
    private String username;

    @Password
    private String password;
    private UserRole userRole;

}