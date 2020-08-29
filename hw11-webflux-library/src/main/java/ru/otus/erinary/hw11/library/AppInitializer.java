package ru.otus.erinary.hw11.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class AppInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppInitializer.class, args);
    }

}
