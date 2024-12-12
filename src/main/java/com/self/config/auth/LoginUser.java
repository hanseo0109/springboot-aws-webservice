package com.self.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 목적 : 반복적으로 호출되는 session 값 가져오기 내용 리펙터링
 * 내용 : 메서드 인자로 세션 값을 바로 받을 수 있도록 ( session 호출 중복 제거 )
 */
@Target(ElementType.PARAMETER)
// ㄴ 위치 지정 , 메서드의 파라미터로 선언된 객체에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
    // ㄴ 어노테이션 클래스 지정 기호
}
