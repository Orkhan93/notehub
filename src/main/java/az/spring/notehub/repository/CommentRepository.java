package az.spring.notehub.repository;

import az.spring.notehub.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUserId(Long userId);

    List<Comment> findByUserIdAndNoteId(Long userId, Long noteId);

}