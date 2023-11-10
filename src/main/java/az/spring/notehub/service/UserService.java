package az.spring.notehub.service;

import az.spring.notehub.entity.User;
import az.spring.notehub.enums.UserRole;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.ChangePasswordRequest;
import az.spring.notehub.request.LoginRequest;
import az.spring.notehub.request.SignupRequest;
import az.spring.notehub.request.UserRequest;
import az.spring.notehub.response.UserResponse;
import az.spring.notehub.response.UserResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User signup(SignupRequest signupRequest) {
        Optional<User> email = userRepository.findByEmailEqualsIgnoreCase(signupRequest.getEmail());
        Optional<User> username = userRepository.findByUsernameEqualsIgnoreCase(signupRequest.getUsername());
        if (email.isPresent() || username.isPresent()) {
            throw new RuntimeException("Bu email ile istifadeci artiq bazaya daxil edilib...");
        } else {
            User user = new User();
            user.setName(signupRequest.getName());
            user.setUserRole(UserRole.USER);
            user.setUsername(signupRequest.getUsername());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(signupRequest.getPassword());
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

    public void changePassword(ChangePasswordRequest changePasswordRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found : "));
        if (Objects.nonNull(user)) {
            if (!user.getPassword().equals(changePasswordRequest.getOldPassword())) {
                throw new RuntimeException("Kohne Password duzgun daxil edilmeyib : ");
            } else if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
                throw new RuntimeException("Tesdiq edilmeli olan password yanlis daxil edilib : ");
            } else
                user.setPassword(changePasswordRequest.getNewPassword());
            userRepository.save(user);
        }
    }

    public User update(UserRequest userRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found : "));
        User response = new User();
        response.setId(user.getId());
        response.setName(userRequest.getName());
        response.setUsername(userRequest.getUsername());
        response.setEmail(userRequest.getEmail());
        response.setPassword(userRequest.getPassword());
        response.setUserRole(user.getUserRole());
        return userRepository.save(response);
    }

    public UserResponseList getAllUsers() {
        List<User> all = userRepository.findAll();
        UserResponseList responseList = new UserResponseList();
        responseList.setUserList(all);
        return responseList;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found : "));
    }

    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not found : "));
        userRepository.delete(user);
    }

}