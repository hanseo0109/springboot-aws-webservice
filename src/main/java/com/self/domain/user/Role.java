package com.self.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role{
    // spring security에서는 `ROLE_` 이 항상 앞에 있어야 함
    GUEST("ROLE_GUEST" , "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
