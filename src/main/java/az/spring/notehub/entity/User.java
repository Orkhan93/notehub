package az.spring.notehub.entity;

import az.spring.notehub.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "notes")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> notes;

    @Column(name = "likes")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes;

    @Column(name = "comments")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Override
    public String toString() {
        return "User{id=%d, name='%s', email='%s', username='%s', password='%s', userRole=%s}"
                .formatted(id, name, email, username, password, userRole);
    }

}