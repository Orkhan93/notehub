package az.spring.notehub.service;

import az.spring.notehub.entity.Like;
import az.spring.notehub.entity.Note;
import az.spring.notehub.entity.User;
import az.spring.notehub.repository.LikeRepository;
import az.spring.notehub.repository.NoteRepository;
import az.spring.notehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                () -> new RuntimeException("User not found : "));
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new RuntimeException("Note not found : "));
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