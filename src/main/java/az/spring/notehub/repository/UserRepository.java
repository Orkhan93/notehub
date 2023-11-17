package az.spring.notehub.repository;

import az.spring.notehub.entity.Note;
import az.spring.notehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailEqualsIgnoreCase(String email);

    Optional<User> findByUsernameEqualsIgnoreCase(String username);

}