package az.spring.notehub.mapper;

import az.spring.notehub.entity.Note;
import az.spring.notehub.request.NoteRequest;
import az.spring.notehub.response.NoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface NoteMapper {

    Note fromRequestToModel(NoteRequest noteRequest);

    NoteResponse fromModelToResponse(Note note);

}