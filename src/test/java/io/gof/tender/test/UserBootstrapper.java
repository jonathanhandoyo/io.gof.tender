package io.gof.tender.test;

import io.gof.tender.Application;
import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.BidRepository;
import io.gof.tender.repository.ProjectRepository;
import io.gof.tender.repository.RepresentativeRepository;
import io.gof.tender.repository.VendorRepository;
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
    private ProjectRepository projects;

    @Autowired
    private BidRepository bids;

    @Autowired
    private VendorRepository vendors;

    @Autowired
    private RepresentativeRepository representatives;

    @Test
    public void fill() throws Exception {
    }

    public static void main(String[] args) throws Exception {
    }
}
