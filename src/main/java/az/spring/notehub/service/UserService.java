package az.spring.notehub.service;

import az.spring.notehub.entity.User;
import az.spring.notehub.enums.UserRole;
import az.spring.notehub.exception.error.ErrorMessage;
import az.spring.notehub.exception.handler.NoteNotFoundException;
import az.spring.notehub.exception.handler.UserNotFoundException;
import az.spring.notehub.mapper.UserMapper;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.ChangePasswordRequest;
import az.spring.notehub.request.LoginRequest;
import az.spring.notehub.request.SignupRequest;
import az.spring.notehub.request.UserRequest;
import az.spring.notehub.response.UserResponse;
import az.spring.notehub.response.UserResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse signup(SignupRequest signupRequest) {
        Optional<User> email = userRepository.findByEmailEqualsIgnoreCase(signupRequest.getEmail());
        Optional<User> username = userRepository.findByUsernameEqualsIgnoreCase(signupRequest.getUsername());
        if (email.isPresent() || username.isPresent()) {
            throw new RuntimeException("Bu email ile istifadeci artiq bazaya daxil edilib...");
        } else {
            User user = userMapper.fromSignRequestToModel(signupRequest);
            user.setUserRole(UserRole.USER);
            return userMapper.fromModelToResponse(userRepository.save(user));
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
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
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

    public UserResponse update(UserRequest userRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        User updated = userMapper.fromRequestToModel(userRequest);
        updated.setId(user.getId());
        updated.setUserRole(user.getUserRole());
        return userMapper.fromModelToResponse(userRepository.save(updated));
    }

    public UserResponseList getAllUsers() {
        UserResponseList responseList = new UserResponseList();
        List<User> all = userRepository.findAll();
        responseList.setUserList(all);
        return responseList;
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        return userMapper.fromModelToResponse(user);
    }

    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        userRepository.delete(user);
    }

}