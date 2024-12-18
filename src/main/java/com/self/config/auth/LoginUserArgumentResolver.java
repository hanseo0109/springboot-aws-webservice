package com.self.config.auth;

import com.self.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

/**
 * 목적 : HandlerMethodArgumentResolver의 구현체가 지정한 값으로 해당 메서드의 `파라미터`로 넘기는 역할을 수행
 * 환경설정 : spring에서 해당 클래스를 인식할 수 있도록 WebMvcConfigurer에 추가
 */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    /**
     * 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
     * 여기서는 파라미터에 @LoginUser 어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true 반환
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation( LoginUser.class ) != null;
        boolean isUserClass = SessionUser.class.equals( parameter.getParameterType() );
        return isLoginUserAnnotation && isUserClass;
    }

    /**
     * 파라미터에 전달할 객체 생성 ( 여기서는 세션에서 객체를 가져옴 )
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter
            , ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest
            , WebDataBinderFactory binderFactory) throws Exception
    {
        return httpSession.getAttribute("user");
    }
}
