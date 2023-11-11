package az.spring.notehub.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoteRequest {

    private Long id;
    private String title;
    private String content;
    private Long userId;

}