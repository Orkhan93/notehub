package az.spring.notehub.mapper;

import az.spring.notehub.entity.Comment;
import az.spring.notehub.request.CommentRequest;
import az.spring.notehub.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment fromRequestToModel(CommentRequest commentRequest);

    CommentResponse fromModelToResponse(Comment comment);

}