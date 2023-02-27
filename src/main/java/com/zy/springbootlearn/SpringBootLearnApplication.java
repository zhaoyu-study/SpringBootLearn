package com.zy.springbootlearn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController //用@GetMapping("/")
@SpringBootApplication
@MapperScan("com.zy.springbootlearn.mapper")
public class SpringBootLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLearnApplication.class, args);
    }


}
