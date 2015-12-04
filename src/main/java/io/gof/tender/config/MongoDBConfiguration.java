package io.gof.tender.config;

import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoDBConfiguration {
    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
        MongoClientURI uri = new MongoClientURI("mongodb://tender_user:tender@ds045464.mongolab.com:45464/tender");
        return new SimpleMongoDbFactory(uri);
    }

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;

    }
}
