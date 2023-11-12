package az.spring.notehub.controller;

import az.spring.notehub.request.CommentRequest;
import az.spring.notehub.response.CommentResponse;
import az.spring.notehub.response.CommentResponseList;
import az.spring.notehub.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(commentRequest));
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest commentRequest,
                                                         @PathVariable(name = "commentId") Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentRequest, commentId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<CommentResponseList> getAllComments() {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments());
    }

    @GetMapping("/getAll/ByUserId/{userId}")
    public ResponseEntity<CommentResponseList> getAllCommentByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentByUserId(userId));
    }

    @GetMapping("/get/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable(name = "commentId") Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentById(commentId));
    }

    @GetMapping("/{userId}/getAll/ByUserIdAndNoteId/{noteId}")
    public ResponseEntity<CommentResponseList> getAllCommentByUserIdAndNoteId(@PathVariable(name = "userId") Long userId,
                                                                              @PathVariable(name = "noteId") Long noteId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentByUserIdAndNoteId(userId, noteId));
    }

    @DeleteMapping("/{userId}/delete/{commentId}")
    public void deleteCommentById(@PathVariable(name = "userId") Long userId,
                                  @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteCommentById(userId, commentId);
    }

}