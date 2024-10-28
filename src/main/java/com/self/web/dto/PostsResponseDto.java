package com.self.web.dto;

import com.self.domain.posts.Posts;
import lombok.Getter;

/*
Posts Entiy의 필드 중 일부만 사용
*/

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.author = posts.getAuthor();
    }
}
