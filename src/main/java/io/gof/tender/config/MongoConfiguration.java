package io.gof.tender.config;

import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        String location = "mongodb://tender_user:tender@ds045464.mongolab.com:45464/tender";
        MongoClientURI uri = new MongoClientURI(location);
        LOG.info(location);
        return new SimpleMongoDbFactory(uri);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(this.mongoDbFactory());
        LOG.info("collections: " + mongoTemplate.getCollectionNames().size());
        return mongoTemplate;
    }
}
