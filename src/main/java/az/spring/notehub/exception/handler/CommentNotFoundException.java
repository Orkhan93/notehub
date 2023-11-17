package az.spring.notehub.exception.handler;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(String code, String message) {
        super(message);
    }

}