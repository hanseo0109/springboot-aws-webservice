package com.self.config.auth;

import com.self.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
// ㄴ spring security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .headers().frameOptions().disable()
                // ㄴ h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
                .and()
                .authorizeRequests()
                // ㄴ URL별 권한 관리를 설정하는 옵션 시작 ( antMatchers 생성 시 필수 선언 )
                .antMatchers(
                        "/"
                        , "/css/**"
                        , "/images/**"
                        , "/js/**"
                        , "/h2-console/**"
                ).permitAll()
                // ㄴ 권한 관리 대상을 설정하는 옵션( URL, HTTP 메서드별로 관리 )
                // ㄴ `/` 등 지정된 URL들은 `permitAll()` 옵션을 통해 전체 열람 권한을 줌

                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                // ㄴ `/api/v1/**` 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록

                .anyRequest().authenticated()
                // ㄴ anyRequest : 설정된 값들 이외 나머지 URL들을 나타낸다
                // ㄴ authenticated : 나머지 URL들은 모두 `로그인한 사용자들만` 허용

                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        // ㄴ 로그아웃 성공 시 `/`주소로 이동

                .and()
                    .oauth2Login()
                    // ㄴ OAuth2 로그인 기능
                        .userInfoEndpoint()
                        // ㄴ OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들 담당
                            .userService(customOAuth2UserService);
                            // ㄴ 소셜 로그인 성공 시 ` 후속 조치 `를 진행할 UserService 인터페이스의 구현체를 등록
                            // ㄴ 리소스 서버( 소셜 서비스들 )에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시 가능
    }
}
