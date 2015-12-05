package io.gof.tender.test;

import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.LocationRepository;
import io.gof.tender.repository.ProjectRepository;
import io.gof.tender.repository.VendorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class MainTest {
    private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);

    @Autowired
    private ProjectRepository projects;

    @Autowired
    private VendorRepository vendors;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LocationRepository locations;

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
    public void test3() throws Exception {
        /*List<ProjectLocation> projectLocations1 = locations.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(1, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations2 = locations.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(2, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations3 = locations.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(3, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations4 = locations.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(4, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations5 = locations.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(5, Metrics.KILOMETERS));
        List<ProjectLocation> projectLocations6 = locations.findByCoordinateNear(new Point(106.8314138, -6.1748441), new Distance(6, Metrics.KILOMETERS));

        System.out.println(projectLocations1);*/
    }
}
