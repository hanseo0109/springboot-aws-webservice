package com.self;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// JPA Audting 활성화
@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 내부 WAS 실행 역할
        SpringApplication.run(Application.class, args); 
    }
}

