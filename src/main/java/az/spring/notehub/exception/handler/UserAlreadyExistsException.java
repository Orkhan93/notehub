package az.spring.notehub.exception.handler;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String code, String message) {
        super(message);
    }

}