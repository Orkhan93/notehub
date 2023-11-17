package az.spring.notehub.response;

import az.spring.notehub.entity.Reminder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReminderResponseList {

    List<Reminder> reminderList;

}