package ru.otus.erinary.hw08.library.dao.config;

import io.mongock.driver.api.driver.ConnectionDriver;
import io.mongock.driver.mongodb.springdata.v4.SpringDataMongoV4Driver;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@TestConfiguration
public class MongockConfig {

    @Bean
    public ConnectionDriver mongockConnectionDriver(final MongoTemplate mongoTemplate) {
        return SpringDataMongoV4Driver.withDefaultLock(mongoTemplate);
    }
}
