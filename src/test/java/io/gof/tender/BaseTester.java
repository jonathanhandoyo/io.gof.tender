package io.gof.tender;

import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.repository.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class BaseTester {
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected CommentRepository comments;

    @Autowired
    protected MilestoneRepository miletones;

    @Autowired
    protected LocationRepository locations;

    @Autowired
    protected MessageRepository messages;

    @Autowired
    protected ProjectRepository projects;

    @Autowired
    protected RevisionRepository revisions;

    @Autowired
    protected VendorRepository vendors;

    @Autowired
    protected UserRepository users;
}
