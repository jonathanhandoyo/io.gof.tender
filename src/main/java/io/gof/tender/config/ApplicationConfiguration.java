package io.gof.tender.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {"io.gof.tender"})
@EnableNeo4jRepositories(basePackages = "io.gof.tender.repository")
@EnableWebMvc
public class ApplicationConfiguration extends Neo4jConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    @Override
    public Neo4jServer neo4jServer() {
        String url = "http://tender:K1I1ZKufPGEEM4ouSAd1@tender.sb02.stations.graphenedb.com:24789";
        LOG.info("neo4j >> server @ " + url);
        return new RemoteServer(url);
    }

    @Bean
    @Override
    public SessionFactory getSessionFactory() {
        String _package = "io.gof.tender.domain";
        LOG.info("neo4j >> models @ " + _package);
        return new SessionFactory(_package);
    }

    @Bean
    @Override
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
