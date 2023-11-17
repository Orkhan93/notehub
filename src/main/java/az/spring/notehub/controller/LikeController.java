package az.spring.notehub.controller;

import az.spring.notehub.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{userId}/like/{noteId}")
    public ResponseEntity<String> likeNote(@PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "noteId") Long noteId) {
        likeService.likeNote(userId, noteId);
        return ResponseEntity.status(HttpStatus.OK).body("Note Liked Successfully !");
    }

    @PostMapping("/{userId}/unlike/{noteId}")
    public ResponseEntity<String> unlike(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "noteId") Long noteId) {
        likeService.unlikeNote(userId, noteId);
        return ResponseEntity.status(HttpStatus.OK).body("Note Unliked Successfully !");
    }

}