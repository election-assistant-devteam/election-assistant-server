package com.runningmate.server;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        SpringApplication.run(ServerApplication.class, args);
    }

}
