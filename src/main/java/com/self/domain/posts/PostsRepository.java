package com.self.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao 역하을 하는 DB Layer 접근 역할
 * CRUD 메소드가 자동으로 생성
 * Repository 추가 필요 없음
 * Entity class ( 여기선 Posts ) 와 Entity Repository는 함께 위치해야 함
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

}
