package com.progzhou.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.progzhou.community.mapper.*")
public class CommunityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityServerApplication.class, args);
    }

}
