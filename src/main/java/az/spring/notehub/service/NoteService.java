package az.spring.notehub.service;

import az.spring.notehub.entity.Note;
import az.spring.notehub.entity.User;
import az.spring.notehub.exception.error.ErrorMessage;
import az.spring.notehub.exception.handler.NoteNotFoundException;
import az.spring.notehub.exception.handler.UserNotFoundException;
import az.spring.notehub.mapper.NoteMapper;
import az.spring.notehub.repository.NoteRepository;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.NoteRequest;
import az.spring.notehub.response.NoteResponse;
import az.spring.notehub.response.NoteResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserRepository userRepository;

    public NoteResponse addNote(NoteRequest noteRequest, Long userId) {
        log.info("Inside noteRequest {}", noteRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        Note note = noteMapper.fromRequestToModel(noteRequest);
        note.setCreationDate(LocalDateTime.now());
        note.setUser(user);
        log.info("Inside note {}", note);
        return noteMapper.fromModelToResponse(noteRepository.save(note));
    }

    public NoteResponse updateNote(NoteRequest noteRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        log.info("Inside user {}", user);
        Note note = noteRepository.findById(noteRequest.getId()).orElseThrow(
                () -> new NoteNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.NOTE_NOT_FOUND));
        log.info("Inside note {}", note);
        Note updated = noteMapper.fromRequestToModel(noteRequest);
        updated.setUser(user);
        updated.setId(note.getId());
        updated.setCreationDate(LocalDateTime.now());
        log.info("Inside updatedNote {}", updated);
        return noteMapper.fromModelToResponse(noteRepository.save(updated));
    }

    public NoteResponse getNoteById(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new NoteNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.NOTE_NOT_FOUND));
        log.info("Inside noteResponse {}", note);
        return noteMapper.fromModelToResponse(note);
    }

    public NoteResponseList getAllNotes() {
        List<Note> allNotes = noteRepository.findAll();
        NoteResponseList responseList = new NoteResponseList();
        responseList.setNoteList(allNotes);
        log.info("Inside allNotes {}", responseList);
        return responseList;
    }

    public void deleteNoteById(Long noteId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new NoteNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.NOTE_NOT_FOUND));
        if (note.getUser().getId() == user.getId()) {
            log.info("Inside deleteNoteById {}", note);
            noteRepository.deleteById(noteId);
        }
    }

}