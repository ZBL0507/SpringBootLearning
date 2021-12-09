package com.zbl.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zbl.springboot.dao")
public class SpringBootLearningApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootLearningApplication.class, args);

    }

}
