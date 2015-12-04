package io.gof.tender.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gof.tender.Application;
import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.domain.*;
import io.gof.tender.repository.*;
import io.gof.tender.util.CustomMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static io.gof.tender.util.CustomMap.map;
import static org.apache.commons.lang3.tuple.Pair.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class MainTest {
    private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);

    @Autowired
    private Neo4jOperations neo4jTemplate;

    @Autowired
    private ProjectRepository projects;

    @Autowired
    private BidRepository bids;

    @Autowired
    private VendorRepository vendors;

    @Autowired
    private RepresentativeRepository representatives;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProjectLocationRepository projectLocationRepo;

    @Test
    public void test1() throws Exception {
        try {
            for (Project project: this.projects.findAll()){
                System.out.println(project);
            }
        } catch (Exception exception) {
            LOG.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Test
    public void test2() throws Exception {
        mongoTemplate.createCollection(ProjectLocation.class);

        ProjectLocation location = new ProjectLocation();
        location.setCoordinate(new double[]{1.3215578,103.8969994});
        //projectLocationRepo.save(location);
        mongoTemplate.save(location);
    }
}
