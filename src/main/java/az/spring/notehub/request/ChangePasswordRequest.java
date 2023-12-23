package az.spring.notehub.request;

import az.spring.notehub.constraint.validation.Password;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordRequest {

    @Password
    private String oldPassword;

    @Password
    private String newPassword;

    @Password
    private String confirmPassword;

}