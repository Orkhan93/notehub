package az.spring.notehub.controller;

import az.spring.notehub.request.ReminderRequest;
import az.spring.notehub.response.ReminderResponse;
import az.spring.notehub.service.ReminderService;
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

    @PostMapping("/send/{userId}")
    public void checkAndSendReminders(@PathVariable(name = "userId") Long userId) {
        reminderService.checkAndSendReminders(userId);
    }

}