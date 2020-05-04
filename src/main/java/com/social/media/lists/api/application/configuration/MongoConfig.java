package com.social.media.lists.api.application.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.social.media.lists.api.application.ServiceConstants;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import de.undercouch.bson4jackson.BsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import java.io.IOException;

@Configuration
public class MongoConfig {

    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);

    @Autowired
    ObjectMapper jsonMapper;

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {

        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(ServiceConstants.MONGO_DB_URL);
        mongo.setPort(ServiceConstants.MONGO_DB_PORT);
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, ServiceConstants.MONGO_DB_NAME);

        return mongoTemplate;
    }

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean jackson2RepositoryPopulatorFactory() {

        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();

        try {

            factory.setMapper(jsonMapper);
            factory.setResources(new Resource[]{new ClassPathResource("data.json")});
            factory.afterPropertiesSet();
        } catch (Exception e) {

            log.error("Could not load social media lists mock data", e);
        }

        return factory;
    }
}
