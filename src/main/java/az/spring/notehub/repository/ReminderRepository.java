package az.spring.notehub.repository;

import az.spring.notehub.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByReminderDateAfter(LocalDateTime dateTime);

}