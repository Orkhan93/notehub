package az.spring.notehub.exception.handler;

public class ReminderNotFoundException extends RuntimeException {

    public ReminderNotFoundException(String code, String message) {
        super(message);
    }

}