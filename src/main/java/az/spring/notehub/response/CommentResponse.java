package az.spring.notehub.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponse {

    private Long id;
    private String content;
    private Long noteId;
    private Long userId;

}