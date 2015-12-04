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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
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

        double[][] coordinates = {
            new double[]{103.870295, -6.349759},
            new double[]{106.841188, -6.169245},
            new double[]{106.805771, -6.181980},
            new double[]{106.805187, -6.177243},
            new double[]{106.827255, -6.183365},
            new double[]{106.822940, -6.169066},
        };

        int i = 0;
        List<Project> projectList = projects.findAll(new PageRequest(0, 6)).getContent();
        for (Project project : projectList){
            ProjectLocation location = new ProjectLocation();
            location.setProjectId(project.getId());
            location.setName(project.getLocation());
            location.setCoordinate(coordinates[i++]);
            projectLocationRepo.save(location);
        }
    }

    @Test
    public void test3() throws Exception {
        /*List<ProjectLocation> projectLocations1 = projectLocationRepo.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(1, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations2 = projectLocationRepo.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(2, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations3 = projectLocationRepo.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(3, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations4 = projectLocationRepo.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(4, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations5 = projectLocationRepo.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(5, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations6 = projectLocationRepo.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(6, Metrics.KILOMETERS));

        System.out.println(projectLocations1);*/
    }
}
