package com.github.lmm1990.blackhode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.github.lmm1990.blackhode.mapper")
public class BlackHodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlackHodeApplication.class, args);
    }
}
