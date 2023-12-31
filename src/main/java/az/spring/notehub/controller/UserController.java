package az.spring.notehub.controller;

import az.spring.notehub.request.ChangePasswordRequest;
import az.spring.notehub.request.LoginRequest;
import az.spring.notehub.request.SignupRequest;
import az.spring.notehub.request.UserRequest;
import az.spring.notehub.response.UserResponse;
import az.spring.notehub.response.UserResponseList;
import az.spring.notehub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest));
    }

    @PutMapping("/changePassword/{userId}")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                               @PathVariable(name = "userId") Long userId) {
        userService.changePassword(changePasswordRequest, userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest userRequest, @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userRequest, userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<UserResponseList> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUserById(@PathVariable(name = "userId") Long userId) {
        userService.deleteUserById(userId);
    }

}