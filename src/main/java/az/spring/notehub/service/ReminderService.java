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

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final ReminderMapper reminderMapper;

    public ReminderResponse createReminder(ReminderRequest reminderRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not found : "));
        Note note = noteRepository.findById(reminderRequest.getNoteId()).orElseThrow(
                () -> new RuntimeException("Note not found : "));
        Reminder reminder = reminderMapper.fromRequestToModel(reminderRequest);
        return null;
    }

}