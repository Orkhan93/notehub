package az.spring.notehub.service;

import az.spring.notehub.entity.User;
import az.spring.notehub.enums.UserRole;
import az.spring.notehub.exception.handler.UserAlreadyExistsException;
import az.spring.notehub.mapper.UserMapper;
import az.spring.notehub.repository.UserRepository;
import az.spring.notehub.request.SignupRequest;
import az.spring.notehub.response.UserResponse;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;


    @Test
    public void testUserSignUp_whenUserSignUpRequest_shouldReturnUserResponse() {
        User user = new User();
        user.setId(1L);
        user.setUserRole(UserRole.USER);
        user.setPassword("password123456");
        user.setName("user-name");
        user.setUsername("user-username");
        user.setEmail("user@mail.com");
        user.setLikes(new ArrayList<>());
        user.setComments(new ArrayList<>());
        user.setNotes(new ArrayList<>());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUserRole(UserRole.USER);
        userResponse.setName("user-name");
        userResponse.setEmail("user@mail.com");
        userResponse.setPassword("password123456");
        userResponse.setUsername("user-username");

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("user-name");
        signupRequest.setEmail("user@mail.com");
        signupRequest.setPassword("password123456");
        signupRequest.setUsername("user-username");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmailEqualsIgnoreCase("user@mail.com")).thenReturn(Optional.of(user));
        when(userRepository.findByUsernameEqualsIgnoreCase("user-username")).thenReturn(Optional.of(user));
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.signup(signupRequest));
        when(userMapper.fromModelToResponse(user)).thenReturn(userResponse);
        when(userRepository.save(user)).thenReturn(user);

        UserResponse signup = userService.signup(signupRequest);
        AssertionsForClassTypes.assertThat(signup).isNotNull();
        Assertions.assertEquals(signup, userResponse);
    }

}