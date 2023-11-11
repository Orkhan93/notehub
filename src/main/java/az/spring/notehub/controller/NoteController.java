package az.spring.notehub.controller;

import az.spring.notehub.request.NoteRequest;
import az.spring.notehub.response.NoteResponse;
import az.spring.notehub.response.NoteResponseList;
import az.spring.notehub.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<NoteResponse> addNote(@RequestBody NoteRequest noteRequest,
                                                @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.addNote(noteRequest, userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<NoteResponse> updateNote(@RequestBody NoteRequest noteRequest,
                                                   @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.updateNote(noteRequest, userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<NoteResponseList> getAllNotes() {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getAllNotes());
    }

    @GetMapping("/get/{noteId}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable(name = "noteId") Long noteId) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNoteById(noteId));
    }

    @DeleteMapping("/{noteId}/delete/{userId}")
    public void deleteNoteById(@PathVariable(name = "noteId") Long noteId,
                               @PathVariable(name = "userId") Long userId) {
        noteService.deleteNoteById(noteId, userId);
    }

}