package az.spring.notehub.service;

import az.spring.notehub.contants.NoteConstant;
import az.spring.notehub.entity.Note;
import az.spring.notehub.entity.Reminder;
import az.spring.notehub.entity.User;
import az.spring.notehub.exception.error.ErrorMessage;
import az.spring.notehub.exception.handler.NoteNotFoundException;
import az.spring.notehub.exception.handler.UserNotFoundException;
import az.spring.notehub.mapper.ReminderMapper;
import az.spring.notehub.repository.NoteRepository;
import az.spring.notehub.repository.ReminderRepository;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.ReminderRequest;
import az.spring.notehub.response.ReminderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final ReminderMapper reminderMapper;
    private final EmailService emailService;

    public ReminderResponse createReminder(ReminderRequest reminderRequest, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Note note = noteRepository.findById(reminderRequest.getNoteId()).orElseThrow(
                () -> new NoteNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.NOTE_NOT_FOUND));
        if (user.isEmpty()) {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND);
        } else {
            Reminder reminder = reminderMapper.fromRequestToModel(reminderRequest);
            reminder.setNote(note);
            return reminderMapper.fromModelToResponse(reminderRepository.save(reminder));
        }
    }

    public void checkAndSendReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Reminder> reminders = reminderRepository.findByReminderDateAfter(now.plusMinutes(10));
        for (Reminder reminder : reminders) {
            User user = reminder.getNote().getUser();
            emailService.sendEmailToUser(user, NoteConstant.EMAIL_SUBJECT, NoteConstant.EMAIL_MESSAGE);
        }
    }

}