package com.self.service.posts;

import com.self.domain.posts.Posts;
import com.self.domain.posts.PostsRepository;
import com.self.web.dto.PostsResponseDto;
import com.self.web.dto.PostsSaveRequestDto;
import com.self.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    // DB 연결 담당
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    /**
     * update 부분에 db 쿼리를 날리는 부분이 없음
     * 이게 가능한 이유는 JPA의 영속성 컨텍스트 떄문
     * ㄴ 영속성 컨텍스트?
     *      ㄴ 엔티티를 영구 저장하는 환경
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException( "id = " + id + " 게시글이 없습니다.")
                );

        posts.update(
                postsUpdateRequestDto.getTitle()
                , postsUpdateRequestDto.getContent()
        );
        return id;
    }

    public PostsResponseDto findById( Long id ){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException( "id = " + id + " 게시글이 없습니다.")
                );
        return new PostsResponseDto(posts);
    }
}
