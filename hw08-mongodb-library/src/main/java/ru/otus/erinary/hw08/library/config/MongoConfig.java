package ru.otus.erinary.hw08.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import ru.otus.erinary.hw08.library.dao.converter.ZoneDateTimeWriteConverter;
import ru.otus.erinary.hw08.library.dao.converter.ZonedDateTimeReadConverter;

import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new ZoneDateTimeWriteConverter(),
                new ZonedDateTimeReadConverter()
        ));
    }
}
