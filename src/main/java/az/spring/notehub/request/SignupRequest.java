package az.spring.notehub.request;

import az.spring.notehub.constraint.validation.Name;
import az.spring.notehub.constraint.validation.Password;
import az.spring.notehub.constraint.validation.Username;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {

    @Name
    private String name;

    @Username
    private String username;

    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Password
    private String password;

}