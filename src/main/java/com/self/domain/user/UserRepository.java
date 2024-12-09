package com.self.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 로그인으로 반환되는 email값을 통해 신규 및 기존 사용자 여부 판단
    Optional<User> findByEmail(String email);
}
