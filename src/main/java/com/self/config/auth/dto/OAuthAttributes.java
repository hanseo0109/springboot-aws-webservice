package com.self.config.auth.dto;

import com.self.domain.user.Role;
import com.self.domain.user.User;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * 해당 클래는 `DTO` 역할을 함
 * 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 추가
 * ㄴ 사용자
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // of : OAuth2User에서 반환하는 사용자 정보는 Map이라 한개씩 반환해야 한다
    public static OAuthAttributes of
    (
        String registrationId
        , String userNameAttributeName
        , Map<String, Object> attributes
    ){
        return ofGoogle( userNameAttributeName, attributes );
    }

    private static OAuthAttributes ofGoogle(
        String userNameAttributes
        , Map<String, Object> attributes
    ){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributes)
                .build();
    }

    /**
     * Entity는 처음 가입할때 생성
     * 가입 시 기본 권한을 GUEST로 부여
     * @return User
     */
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
