package com.xxzy.EXLG;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRabbit
@EnableRedisHttpSession
@SpringBootApplication
@ComponentScan("com.xxzy")
@MapperScan("com.xxzy.EXLG.dao")
public class EXLGApplication {
    public static void main(String[] args) {
        SpringApplication.run(EXLGApplication.class,args);
    }
}
