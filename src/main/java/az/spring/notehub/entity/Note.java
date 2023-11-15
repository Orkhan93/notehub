package az.spring.notehub.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-mm-yyyy'T'HH:mm")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @Column(name = "likes")
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<Like> likes;

    @Column(name = "notes")
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToOne(mappedBy = "note")
    private Reminder reminder;

    @Override
    public String toString() {
        return "Note{id=%d, title='%s', content='%s', creationDate=%s}"
                .formatted(id, title, content, creationDate);
    }

}