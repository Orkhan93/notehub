package az.spring.notehub.response;

import az.spring.notehub.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CommentResponseList {

    List<Comment> commentList;

}