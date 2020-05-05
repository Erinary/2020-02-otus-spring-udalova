package ru.otus.erinary.hw05.jdbclibrary;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppInitializer.class, args);

        //TODO for debug
        Console.main(args);
    }

}
