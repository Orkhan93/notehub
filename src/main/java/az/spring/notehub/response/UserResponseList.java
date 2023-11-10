package az.spring.notehub.response;

import az.spring.notehub.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserResponseList {

    List<User> userList;

}