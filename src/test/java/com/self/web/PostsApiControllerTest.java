package com.self.web;

import com.self.domain.posts.Posts;
import com.self.domain.posts.PostsRepository;
import com.self.web.dto.PostsSaveRequestDto;
import com.self.web.dto.PostsUpdateRequestDto;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// WebMvcTest 및 JPA 기능까지 한번에 테스트 할때 사용
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    private String local_url = "http://localhost:";
    private String api_path = "/api/v1/";

    @LocalServerPort
    private int port;

    // WebMvcTest 및 JPA 기능까지 한번에 테스트 할때 사용
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After("")
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception{
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto saveRequsetDto = PostsSaveRequestDto.builder()
                                                                .title(title)
                                                                .content(content)
                                                                .author("author")
                                                                .build();

        String url = local_url + port + api_path + "/posts";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(
                                                    url
                                                    , saveRequsetDto
                                                    , Long.class
                                                );

        // then
        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody())
                .isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() throws Exception{

        // given
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savePosts.getId();

        String expectedTitle = "title";
        String expectedContent = "content";

        PostsUpdateRequestDto updateRequestDto = PostsUpdateRequestDto.builder()
            .title(expectedTitle)
            .content(expectedContent)
            .build();

        String url = local_url + port + api_path + "posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(
                url ,
                HttpMethod.PUT ,
                requestEntity ,
                Long.class
        );

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // isGreaterThan : 숫자형 값이 특정 값 보다 큰지 테스트
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

    }
}