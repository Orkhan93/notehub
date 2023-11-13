package az.spring.notehub.mapper;

import az.spring.notehub.entity.Reminder;
import az.spring.notehub.request.ReminderRequest;
import az.spring.notehub.response.ReminderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ReminderMapper {

    Reminder fromRequestToModel(ReminderRequest reminderRequest);

    @Mapping(source = "note.id", target = "noteId")
    ReminderResponse fromModelToResponse(Reminder reminder);

}