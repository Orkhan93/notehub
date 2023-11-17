package az.spring.notehub.exception.handler;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(String code, String message) {
        super(message);
    }

}