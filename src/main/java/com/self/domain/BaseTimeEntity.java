package com.self.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 해당 클래스를 상속받으면 `입력날짜` 및 `수정날짜`가 자동으로 입력된다.
 */
@Getter
// JPA Entity 클래스들이 해당 클래스를 상속할 경우 필드들로 칼람으로 인식하도록 하는 역할
@MappedSuperclass
// 해당 클래스의 Auditing 기능을 포함시킴
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
