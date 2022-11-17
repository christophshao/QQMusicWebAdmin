package com.javaclient.qqmusicwebserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.javaclient.qqmusicwebserver.dao")
public class QqMusicWebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QqMusicWebServerApplication.class, args);
    }

}
