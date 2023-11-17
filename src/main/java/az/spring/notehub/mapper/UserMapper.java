package az.spring.notehub.mapper;

import az.spring.notehub.entity.User;
import az.spring.notehub.request.SignupRequest;
import az.spring.notehub.request.UserRequest;
import az.spring.notehub.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse fromModelToResponse(User user);

    User fromRequestToModel(UserRequest userRequest);

    User fromSignRequestToModel(SignupRequest signupRequest);

}