package az.spring.notehub.service;

import az.spring.notehub.entity.User;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.LoginRequest;
import az.spring.notehub.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User signup(UserRequest userRequest) {
        Optional<User> email = userRepository.findByEmailEqualsIgnoreCase(userRequest.getEmail());
        Optional<User> username = userRepository.findByUsernameEqualsIgnoreCase(userRequest.getUsername());
        if (email.isPresent() || username.isPresent()) {
            throw new RuntimeException("Bu email ile istifadeci artiq bazaya daxil edilib...");
        } else {
            User user = new User();
            user.setName(userRequest.getName());
            user.setUserRole(userRequest.getUserRole());
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            return userRepository.save(user);
        }
    }

    public String login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmailEqualsIgnoreCase(loginRequest.getEmail());
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(loginRequest.getPassword())) {
            return "Successfully registered.";
        } else
            return "Bad credentials.";
    }

}