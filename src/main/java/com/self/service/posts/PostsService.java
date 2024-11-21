package com.self.service.posts;

import com.self.domain.posts.Posts;
import com.self.domain.posts.PostsRepository;
import com.self.web.dto.PostsListResponseDto;
import com.self.web.dto.PostsResponseDto;
import com.self.web.dto.PostsSaveRequestDto;
import com.self.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * ( readOnly = true ) 설명
     * ㄴ 트랜잭션 범위 유지 및 조회 속도 개선
     * ㄴ 등록, 수정, 삭제 기능이 전혀 없는 메서드에 사용
     *
     * PostsListResponseDto::new 설명
     * ㄴ postsRepository 결과로 넘어온 Posts의 Streamdㅡㄹ map을 통해 PostsListResponseDto 변환 -> List로 변환하는 메서드
     */
    @Transactional( readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return (List<PostsListResponseDto>) postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());      // List로 반환
    }
}
