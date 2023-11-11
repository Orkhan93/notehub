package az.spring.notehub.service;

import az.spring.notehub.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

}