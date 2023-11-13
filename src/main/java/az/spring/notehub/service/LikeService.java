package az.spring.notehub.service;

import az.spring.notehub.entity.Like;
import az.spring.notehub.entity.Note;
import az.spring.notehub.entity.User;
import az.spring.notehub.exception.error.ErrorMessage;
import az.spring.notehub.exception.handler.NoteNotFoundException;
import az.spring.notehub.exception.handler.UserNotFoundException;
import az.spring.notehub.repository.LikeRepository;
import az.spring.notehub.repository.NoteRepository;
import az.spring.notehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public boolean hasUserLiked(Long userId, Long noteId) {
        return likeRepository.existsByUserIdAndNoteId(userId, noteId);
    }

    public void likeNote(Long userId, Long noteId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new NoteNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.NOTE_NOT_FOUND));
        if (!hasUserLiked(userId, noteId)) {
            Like like = new Like();
            like.setUser(user);
            like.setNote(note);
            likeRepository.save(like);
        }
    }

    public void unlikeNote(Long userId, Long noteId) {
        if (hasUserLiked(userId, noteId)) {
            Like like = likeRepository.findByUserIdAndNoteId(userId, noteId);
            likeRepository.delete(like);
        }
    }

}