package com.self.config.auth;

import com.self.config.auth.dto.OAuthAttributes;
import com.self.config.auth.dto.SessionUser;
import com.self.domain.user.User;
import com.self.domain.user.UserRepository;
import com.sun.xml.internal.rngom.digested.DAttributePattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 구글 로그인 이후 가져온 사용자의 정보( email , name, picture 등 ) 들을 기반으로
 * 가입 및 정보수정, 세션 저장 등의 기능을 지원한다.
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();
                // ㄴ 현재 로그인 진행 중인 서비스를 구분하는 코드
                // ㄴ 타 서비스 로그인 연동 시에 구분( 네이버, 구글 ) 용도로 사용

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
                // ㄴ primary-key
                // ㄴ 네이버 로그인과 구글 로그인을 동시 지원할 때 사용 됨

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // ㄴ 해당 클래스를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        // ㄴ 이후 네이버 등 다른 소셜 로그인에서도 사용 됨

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user" , new SessionUser(user));
        // ㄴ 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        // ㄴ (질문) 왜 User class를 사용하지 않고 SessionUser로 만들어서 쓰는지 ?

        return new DefaultOAuth2User(
                Collections.singleton( new SimpleGrantedAuthority(user.getRoleKey() ) )
                , attributes.getAttributes()
                , attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate( OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map( entity -> entity.update( attributes.getName() , attributes.getPicture() ) )
                // ㄴ entity는 `User` class를 가리킴
                // ㄴ 사용자 정보 update ( 이름, 프로필 사진 )
                .orElse( attributes.toEntity() );
        return userRepository.save(user);
    }
}

