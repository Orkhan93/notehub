package az.spring.notehub.request;

import az.spring.notehub.constraint.validation.Password;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Password
    private String password;

}