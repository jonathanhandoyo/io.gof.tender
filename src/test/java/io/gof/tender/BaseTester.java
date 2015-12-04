package io.gof.tender;
import io.gof.tender.Application;
import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.repository.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class BaseTester {
    @Autowired
    protected Neo4jOperations neo4jTemplate;

    @Autowired
    protected BidRepository bids;

    @Autowired
    protected ProjectRepository projects;

    @Autowired
    protected RepresentativeRepository representatives;

    @Autowired
    protected UserRepository users;

    @Autowired
    protected VendorRepository vendors;
}
