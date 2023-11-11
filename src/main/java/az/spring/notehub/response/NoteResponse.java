package az.spring.notehub.response;

import az.spring.notehub.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NoteResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime creationDate;

    @JsonIgnore
    private User user;

}