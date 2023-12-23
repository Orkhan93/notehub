package az.spring.notehub.service;

import az.spring.notehub.contants.NoteConstant;
import az.spring.notehub.entity.User;
import az.spring.notehub.enums.UserRole;
import az.spring.notehub.exception.error.ErrorMessage;
import az.spring.notehub.exception.handler.IncorrectPasswordException;
import az.spring.notehub.exception.handler.UserAlreadyExistsException;
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
    private final EncryptionService encryptionService;
    private final JwtService jwtService;

    public UserResponse signup(SignupRequest signupRequest) {
        log.info("Inside signupRequest {}", signupRequest);
        Optional<User> email = userRepository.findByEmailEqualsIgnoreCase(signupRequest.getEmail());
        Optional<User> username = userRepository.findByUsernameEqualsIgnoreCase(signupRequest.getUsername());
        if (email.isPresent() || username.isPresent()) {
            throw new UserAlreadyExistsException(HttpStatus.CONFLICT.name(), ErrorMessage.ALREADY_EXISTS);
        } else {
            User user = userMapper.fromSignRequestToModel(signupRequest);
            user.setUserRole(UserRole.USER);
            user.setPassword(encryptionService.encryptPassword(signupRequest.getPassword()));
            log.info("Inside signup {}", user);
            return userMapper.fromModelToResponse(userRepository.save(user));
        }
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailEqualsIgnoreCase(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (encryptionService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            return jwtService.generateJwt(user);
        } else
            return NoteConstant.BAD_CREDENTIALS;
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest, Long userId) {
        log.info("Inside changePasswordRequest {}", changePasswordRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user)) {
            if (!user.getPassword().equals(changePasswordRequest.getOldPassword())) {
                throw new IncorrectPasswordException(HttpStatus.BAD_REQUEST.name(), ErrorMessage.OLD_PASSWORD_IS_INCORRECT);
            } else if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
                throw new IncorrectPasswordException(HttpStatus.BAD_REQUEST.name(), ErrorMessage.NOT_MATCHES);
            } else
                user.setPassword(changePasswordRequest.getNewPassword());
            log.info("Inside changePassword {}", user);
            userRepository.save(user);
        }
    }

    public UserResponse update(UserRequest userRequest, Long userId) {
        log.info("Inside userRequest {}", userRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        User updated = userMapper.fromRequestToModel(userRequest);
        updated.setId(user.getId());
        updated.setUserRole(user.getUserRole());
        log.info("Inside updatedUser {}", updated);
        return userMapper.fromModelToResponse(userRepository.save(updated));
    }

    public UserResponseList getAllUsers() {
        UserResponseList responseList = new UserResponseList();
        List<User> all = userRepository.findAll();
        responseList.setUserList(all);
        log.info("Inside getAllUsers {}", responseList);
        return responseList;
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        log.info("Inside getUserById {}", user);
        return userMapper.fromModelToResponse(user);
    }

    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        log.info("Inside deleteUserById {}", user);
        userRepository.delete(user);
    }

}