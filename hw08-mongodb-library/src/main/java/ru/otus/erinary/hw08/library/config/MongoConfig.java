package ru.otus.erinary.hw08.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfig {

    @Bean("MongoTransactionManager")
    public MongoTransactionManager mongoTransactionManager(final MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);
    }

}
