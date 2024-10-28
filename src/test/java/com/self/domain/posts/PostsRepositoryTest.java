package com.self.domain.posts;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// H2 데이터베이스를 자동으로 실행해주는 역할을 함
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // Junit에서 단위 테스트가 끝날 때마다 수행되는 메서드를 지정
    // 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용
    @After("")
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_블러오기(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // save
        postsRepository.save(
            Posts.builder()     // 테이블 posts에 insert/update 쿼리를 실행한다. ( id 존재하지 않으면 insert , 존재하면 update )
                .title(title)
                .content(content)
                .author("hs@gamil.com")
                .build()
        );

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        // given
        LocalDateTime now = LocalDateTime.of(
                2019,
                6,
                4,
                0,
                0,
                0
        );

        postsRepository.save(
                Posts.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .build()
        );

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>> createDate="
                + posts.getCreateDate()
                +" , modifiedDate="
                + posts.getModifiedDate()
        );
        System.out.println("now = " + now);

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}