package az.spring.notehub.service;

import az.spring.notehub.entity.Comment;
import az.spring.notehub.entity.Note;
import az.spring.notehub.entity.User;
import az.spring.notehub.mapper.CommentMapper;
import az.spring.notehub.repository.CommentRepository;
import az.spring.notehub.repository.NoteRepository;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.CommentRequest;
import az.spring.notehub.response.CommentResponse;
import az.spring.notehub.response.CommentResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final CommentMapper commentMapper;

    public CommentResponse addComment(CommentRequest commentRequest) {
        User user = userRepository.findById(commentRequest.getUserId()).orElseThrow(
                () -> new RuntimeException("User Not found : "));
        Note note = noteRepository.findById(commentRequest.getNoteId()).orElseThrow(
                () -> new RuntimeException("Note not found : "));
        Comment comment = commentMapper.fromRequestToModel(commentRequest);
        comment.setUser(user);
        comment.setNote(note);
        return commentMapper.fromModelToResponse(commentRepository.save(comment));
    }

    public CommentResponse updateComment(CommentRequest commentRequest, Long commentId) {
        User user = userRepository.findById(commentRequest.getUserId()).orElseThrow(
                () -> new RuntimeException("User Not found : "));
        Note note = noteRepository.findById(commentRequest.getNoteId()).orElseThrow(
                () -> new RuntimeException("Note not found : "));
        if (Objects.nonNull(user) && Objects.nonNull(note)) {
            Optional<Comment> comment = commentRepository.findById(commentId);
            if (comment.isEmpty()) {
                throw new RuntimeException("Comment not found : ");
            } else {
                Comment updated = commentMapper.fromRequestToModel(commentRequest);
                updated.setId(commentId);
                updated.setNote(note);
                updated.setUser(user);
                return commentMapper.fromModelToResponse(commentRepository.save(updated));
            }
        } else
            throw new RuntimeException("Bad request : ");
    }

    public CommentResponse getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("Comment not found : "));
        return commentMapper.fromModelToResponse(comment);
    }

    public CommentResponseList getAllCommentByUserId(Long userId) {
        CommentResponseList responseList = new CommentResponseList();
        List<Comment> comments = commentRepository.findByUserId(userId);
        responseList.setCommentList(comments);
        return responseList;
    }

    public CommentResponseList getAllCommentByUserIdAndNoteId(Long userId, Long noteId) {
        CommentResponseList responseList = new CommentResponseList();
        List<Comment> comments = commentRepository.findByUserIdAndNoteId(userId, noteId);
        responseList.setCommentList(comments);
        return responseList;
    }

    public CommentResponseList getAllComments() {
        CommentResponseList responseList = new CommentResponseList();
        List<Comment> comments = commentRepository.findAll();
        responseList.setCommentList(comments);
        return responseList;
    }

    public void deleteCommentById(Long userId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not found : "));
        List<Comment> comments = commentRepository.findByUserId(user.getId());
        for (Comment comment : comments) {
            if (commentId == comment.getId())
                commentRepository.deleteById(commentId);
            else
                throw new RuntimeException("Bu id ile comment tapilmadi : ");
        }
    }

}