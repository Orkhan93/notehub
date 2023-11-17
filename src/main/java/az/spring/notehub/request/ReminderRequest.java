package az.spring.notehub.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReminderRequest {

    private Long id;
    private LocalDateTime reminderDate;
    private Long noteId;

}