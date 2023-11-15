package az.spring.notehub.service;

import az.spring.notehub.entity.Note;
import az.spring.notehub.entity.Reminder;
import az.spring.notehub.entity.User;
import az.spring.notehub.mapper.ReminderMapper;
import az.spring.notehub.repository.NoteRepository;
import az.spring.notehub.repository.ReminderRepository;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.ReminderRequest;
import az.spring.notehub.response.ReminderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final ReminderMapper reminderMapper;
    private final NotificationService notificationService;
    private final EmailService emailService;

    public ReminderResponse createReminder(ReminderRequest reminderRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not found : "));
        Note note = noteRepository.findById(reminderRequest.getNoteId()).orElseThrow(
                () -> new RuntimeException("Note not found : "));
        Reminder reminder = reminderMapper.fromRequestToModel(reminderRequest);
        reminder.setNote(note);
        return reminderMapper.fromModelToResponse(reminder);
    }

    public void checkAndSendReminders() {
        List<Reminder> reminders = reminderRepository.findByReminderDateBefore(LocalDateTime.now());
        for (Reminder reminder : reminders) {
            LocalDateTime reminderDate = reminder.getReminderDate();
            /* xatirlatma vaxti elan olunub : ayin 25i ucun
            notun yaranma vaxti(qeydiyyati) elan olunub : ayin 10u
            yoxlanilma vaxti da indiki vaxt goturulecek : LocalDateTime.now();
            */
        }
    }

}