package io.gof.tender.test;

import io.gof.tender.Application;
import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.domain.Project;
import io.gof.tender.domain.User;
import io.gof.tender.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
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

import java.io.FileReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static io.gof.tender.util.CustomMap.map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class UserBootstrapper {
    private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private Neo4jOperations neo4jTemplate;

    @Autowired
    private BidRepository bids;

    @Autowired
    private ProjectRepository projects;

    @Autowired
    private RepresentativeRepository representatives;

    @Autowired
    private UserRepository users;

    @Autowired
    private VendorRepository vendors;

    @Test
    public void wipe() throws Exception {
        this.users.deleteAll();
    }

    @Test
    public void fill() throws Exception {
        this.users.save(new User("admin", "admin", null, null));
        this.users.save(new User("jonathan", "jonathan", "https://drive.google.com/file/d/0Bygmy11KkxT6SmpVeDN3aDE3dHc/view?usp=sharing", null));
        this.users.save(new User("siwananda", "siwananda", null, null));
        this.users.save(new User("arnold", "arnold", null, null));
    }

    public static void main(String[] args) throws Exception {
    }
}
