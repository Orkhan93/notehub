package az.spring.notehub.response;

import az.spring.notehub.entity.Note;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NoteResponseList {

    List<Note> noteList;

}