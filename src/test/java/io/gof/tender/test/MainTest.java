package io.gof.tender.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gof.tender.Application;
import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.domain.*;
import io.gof.tender.repository.BidRepository;
import io.gof.tender.repository.ProjectRepository;
import io.gof.tender.repository.RepresentativeRepository;
import io.gof.tender.repository.VendorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Test
    public void test1() throws Exception {
        try {
            Project project = new Project(
                    "project name",
                    Project.Status.PENDING,
                    "project category",
                    "project description",
                    new Project.Methodology(
                            "project methodology documentation",
                            "project methodology evaluation",
                            "project methodology procurement",
                            "project methodology qualification"
                    ),
                    new Project.Valuation(10D, 9D),
                    new Project.Contract(
                            "project contract payment method",
                            "project contract fiscal year imposition",
                            "project contract funding source"
                    ),
                    "budget reference",
                    "business qualification",
                    null,
                    new Project.Requirement(
                            new Project.Permit(
                                    "project requirement permit type",
                                    "project requirement permit classification"
                            ),
                            new String[] {
                                    "project requirement item 1",
                                    "project requirement item 2"
                            }
                    ),
                    null,
                    null,
                    null
            );

            project = this.projects.save(project);
            System.out.println(project);
        } catch (Exception exception) {
            LOG.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Test
    public void test2() throws Exception {
        String json = "{\"results\":[{\"address_components\":[{\"long_name\":\"12780\",\"short_name\":\"12780\",\"types\":[\"postal_code\"]},{\"long_name\":\"Pancoran\",\"short_name\":\"Pancoran\",\"types\":[\"administrative_area_level_4\",\"political\"]},{\"long_name\":\"Pancoran\",\"short_name\":\"Pancoran\",\"types\":[\"administrative_area_level_3\",\"political\"]},{\"long_name\":\"South Jakarta City\",\"short_name\":\"Kota Jakarta Selatan\",\"types\":[\"administrative_area_level_2\",\"political\"]},{\"long_name\":\"Special Capital Region of Jakarta\",\"short_name\":\"Special Capital Region of Jakarta\",\"types\":[\"administrative_area_level_1\",\"political\"]},{\"long_name\":\"Indonesia\",\"short_name\":\"ID\",\"types\":[\"country\",\"political\"]}],\"formatted_address\":\"Pancoran, Pancoran, South Jakarta City, Special Capital Region of Jakarta 12780, Indonesia\",\"geometry\":{\"bounds\":{\"northeast\":{\"lat\":-6.2408261,\"lng\":106.847168},\"southwest\":{\"lat\":-6.253495600000001,\"lng\":106.8348464}},\"location\":{\"lat\":-6.2461535,\"lng\":106.8399623},\"location_type\":\"APPROXIMATE\",\"viewport\":{\"northeast\":{\"lat\":-6.2408261,\"lng\":106.847168},\"southwest\":{\"lat\":-6.253495600000001,\"lng\":106.8348464}}},\"partial_match\":true,\"place_id\":\"ChIJfQnaELHzaS4RYDYDTevFABw\",\"types\":[\"postal_code\"]}],\"status\":\"OK\"}";

        Location location = Location.fromJson(json);
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(location));
    }
}
