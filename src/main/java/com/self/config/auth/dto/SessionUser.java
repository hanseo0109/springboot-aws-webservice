package com.self.config.auth.dto;

import com.self.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 인증된 사용자 정보만 필요
 * User class를 사용하지 않고 SessionUser을 사용한 이유는 `직렬화`를 사용하기 위함
 * User class는 `Entity` 이기 떄문에 언제 다른 Entity와 관계가 형성될지 모름
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
