package az.spring.notehub.service;

import az.spring.notehub.contants.NoteConstant;
import az.spring.notehub.entity.Note;
import az.spring.notehub.entity.Reminder;
import az.spring.notehub.entity.User;
import az.spring.notehub.exception.error.ErrorMessage;
import az.spring.notehub.exception.handler.NoteNotFoundException;
import az.spring.notehub.exception.handler.ReminderNotFoundException;
import az.spring.notehub.exception.handler.UserNotFoundException;
import az.spring.notehub.mapper.ReminderMapper;
import az.spring.notehub.repository.NoteRepository;
import az.spring.notehub.repository.ReminderRepository;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.ReminderRequest;
import az.spring.notehub.response.ReminderResponse;
import az.spring.notehub.response.ReminderResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        log.info("Inside reminderRequest {}", reminderRequest);
        Optional<User> user = userRepository.findById(userId);
        Note note = noteRepository.findById(reminderRequest.getNoteId()).orElseThrow(
                () -> new NoteNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.NOTE_NOT_FOUND));
        if (user.isEmpty()) {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND);
        } else {
            Reminder reminder = reminderMapper.fromRequestToModel(reminderRequest);
            reminder.setNote(note);
            log.info("Inside createReminder {}", reminder);
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

//    public void checkAndSendRemindersXXX() {
//        LocalDateTime now = LocalDateTime.now();
//        List<Reminder> reminders = reminderRepository.findByReminderDateAfter(now);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        for (Reminder reminder : reminders) {
//            LocalDateTime time = reminder.getReminderDate().minusDays(3);
//            if (now == time) {
//                User user = reminder.getNote().getUser();
//                emailService.sendEmailToUser(user, NoteConstant.EMAIL_SUBJECT, NoteConstant.EMAIL_MESSAGE);
//            }
//        }
//    }

    public ReminderResponse updateReminder(ReminderRequest reminderRequest, Long userId) {
        log.info("Inside reminderRequest {}", reminderRequest);
        Note note = noteRepository.findById(reminderRequest.getNoteId()).orElseThrow(
                () -> new NoteNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.NOTE_NOT_FOUND));
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND);
        } else {
            Optional<Reminder> findReminder = reminderRepository.findById(reminderRequest.getId());
            if (findReminder.isEmpty()) {
                throw new ReminderNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.REMINDER_NOT_FOUND);
            } else {
                Reminder updatedReminder = reminderMapper.fromRequestToModel(reminderRequest);
                updatedReminder.setNote(note);
                log.info("Inside updateReminder {}", updatedReminder);
                return reminderMapper.fromModelToResponse(reminderRepository.save(updatedReminder));
            }
        }
    }

    public ReminderResponseList getAllReminders() {
        List<Reminder> all = reminderRepository.findAll();
        ReminderResponseList responseList = new ReminderResponseList();
        responseList.setReminderList(all);
        log.info("Inside getAllReminders {}", responseList);
        return responseList;
    }

    public ReminderResponse getReminderById(Long reminderId) {
        Reminder reminder = reminderRepository.findById(reminderId).orElseThrow(
                () -> new ReminderNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.REMINDER_NOT_FOUND));
        log.info("Inside getReminderById {}", reminder);
        return reminderMapper.fromModelToResponse(reminder);
    }

    public void deleteReminderById(Long userId, Long reminderId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user)) {
            Reminder reminder = reminderRepository.findById(reminderId).orElseThrow(
                    () -> new ReminderNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.REMINDER_NOT_FOUND));
            if (reminder.getNote().getUser() == user) {
                log.info("Inside deleteReminderById {}", reminder);
                reminderRepository.deleteById(reminderId);
            }
        }
    }

}