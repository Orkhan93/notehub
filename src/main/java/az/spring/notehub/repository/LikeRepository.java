package az.spring.notehub.repository;

import az.spring.notehub.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {


}