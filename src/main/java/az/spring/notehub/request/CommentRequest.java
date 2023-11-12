package az.spring.notehub.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequest {

    private String content;
    private Long noteId;
    private Long userId;

}