package az.spring.notehub.repository;

import az.spring.notehub.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {



}