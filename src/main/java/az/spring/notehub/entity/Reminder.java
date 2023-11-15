package az.spring.notehub.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reminderDate")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime reminderDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_id")
    private Note note;

    @Override
    public String toString() {
        return "Reminder{id=%d, reminderDate=%s}".formatted(id, reminderDate);
    }

}