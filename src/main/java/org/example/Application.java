package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@Configuration
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
