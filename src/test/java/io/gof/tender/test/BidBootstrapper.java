package io.gof.tender.test;

import io.gof.tender.Application;
import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.domain.Bid;
import io.gof.tender.domain.Project;
import io.gof.tender.domain.User;
import io.gof.tender.domain.Vendor;
import io.gof.tender.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
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
public class BidBootstrapper {
    private static final Logger LOG = LoggerFactory.getLogger(BidBootstrapper.class);

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
        int i = 0;
        Reader in = new FileReader("C:\\git\\io.gof.tender\\src\\test\\resources\\data_bids_v1.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        for (CSVRecord record : records) {
            try {
                String vendorId = record.get("nomor_registrasi");
                Vendor vendor = this.vendors.findByBusinessRegistrationId(vendorId);
                if (vendor == null) {
                    vendor = new Vendor(
                            record.get("nomor_registrasi"),
                            record.get("name"),
                            null,
                            null
                    );
                    vendor = this.vendors.save(vendor);
                }

                String projectCode = record.get("pageUrl").substring("https://lpse.lkpp.go.id/eproc/rekanan/lelangpeserta/".length());
                Project project = this.projects.findByCode(projectCode);
                if (project == null) {
                    throw new Exception("project not found");
                }

                Bid bid = new Bid(
                        new Bid.Evaluation(
                                Boolean.parseBoolean(record.get("administrasi")),
                                Boolean.parseBoolean(record.get("teknis")),
                                false,
                                false,
                                Boolean.parseBoolean(record.get("winner"))
                        ),
                        new Bid.Price(
                                NumberUtils.toDouble(record.get("harga_penawaran")),
                                NumberUtils.toDouble(record.get("harga_terkoreksi"))
                        ),
                        0d,
                        "reason",
                        null,
                        vendor
                );

                bid = this.bids.save(bid);
                this.bids.bid(vendor, project, bid);

                if (Boolean.parseBoolean(record.get("winner"))) {
                    this.projects.setWinner(project, vendor);
                }

                System.out.println(++i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws Exception {
        for (User user: this.users.findAll()) {
            System.out.println(user);
        }
    }
}
