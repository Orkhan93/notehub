package az.spring.notehub.controller;

import az.spring.notehub.request.ReminderRequest;
import az.spring.notehub.response.ReminderResponse;
import az.spring.notehub.response.ReminderResponseList;
import az.spring.notehub.service.ReminderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ReminderResponse> createReminder(@RequestBody ReminderRequest reminderRequest,
                                                           @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reminderService.createReminder(reminderRequest, userId));
    }

    @PostMapping("/send")
    public void checkAndSendReminders() {
        reminderService.checkAndSendReminders();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ReminderResponse> updateReminder(@RequestBody ReminderRequest reminderRequest,
                                                           @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(reminderService.updateReminder(reminderRequest, userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ReminderResponseList> allReminders() {
        return ResponseEntity.status(HttpStatus.OK).body(reminderService.getAllReminders());
    }

    @GetMapping("/get/{reminderId}")
    public ResponseEntity<ReminderResponse> getReminderById(@PathVariable(name = "reminderId") Long reminderId) {
        return ResponseEntity.status(HttpStatus.OK).body(reminderService.getReminderById(reminderId));
    }

    @DeleteMapping("/{userId}/delete/{reminderId}")
    public void deleteReminderById(@PathVariable(name = "userId") Long userId,
                                   @PathVariable(name = "reminderId") Long reminderId) {
        reminderService.deleteReminderById(userId, reminderId);
    }

}