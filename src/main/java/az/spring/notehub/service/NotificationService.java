package az.spring.notehub.service;

import az.spring.notehub.entity.User;
import az.spring.notehub.exception.error.ErrorMessage;
import az.spring.notehub.exception.handler.UserNotFoundException;
import az.spring.notehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public void sendNotificationToUser(String subject, String message, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        log.info("Inside users {}", user);
        emailService.sendEmailToUser(user, subject, message);
    }

}