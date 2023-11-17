package az.spring.notehub.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReminderResponse {

    private LocalDateTime reminderDate;
    private Long noteId;

}