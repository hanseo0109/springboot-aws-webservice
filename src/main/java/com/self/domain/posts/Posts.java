package com.self.domain.posts;

import com.self.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// lombok annotation
@Getter
@NoArgsConstructor      // 기본 생성자 자동 추가
// jpa require annotation
@Entity     // 테이블과 링크될 클래스임을 나타낸다
public class Posts extends BaseTimeEntity {
    // 테이블 pk
    @Id
    // pk 생성 규칙
    // spring boot 2.0에서 해당 옵션을 추가해야만 auto_increment가 됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // default 모두 Column 적용, 아래 처럼 option이 필요한 경우만 기재
    @Column( length = 500, nullable = false )
    private String title;

    // TEXT type = content
    @Column( columnDefinition = "TEXT" , nullable = false )
    private String content;

    private String author;

    // Builder를 사용하는 이유
    // ㄴ 파라미터 순서 변경 방지
    @Builder        // 빌더 패턴 적용 , 생성자에 포함된 필드만 빌더
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
