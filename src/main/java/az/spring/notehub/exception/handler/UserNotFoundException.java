package az.spring.notehub.exception.handler;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String code, String message) {
        super(message);
    }

}