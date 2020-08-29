package ru.otus.erinary.hw11.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import ru.otus.erinary.hw11.library.dao.converter.ZoneDateTimeWriteConverter;
import ru.otus.erinary.hw11.library.dao.converter.ZonedDateTimeReadConverter;
import ru.otus.erinary.hw11.library.dao.listener.BookEventListener;
import ru.otus.erinary.hw11.library.dao.listener.CommentEventListener;

import java.util.List;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "ru.otus.erinary.hw11.library.dao")
@EnableMongoRepositories(basePackages = "ru.otus.erinary.hw11.library.dao")
public class MongoConfig {
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new ZoneDateTimeWriteConverter(),
                new ZonedDateTimeReadConverter()
        ));
    }

    @Bean
    public BookEventListener bookEventListener() {
        return new BookEventListener();
    }

    @Bean
    public CommentEventListener commentEventListener() {
        return new CommentEventListener();
    }
}
