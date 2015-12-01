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
//            Project project = new Project();
//            project.setName("Pengadaan Jasa Konsultansi Badan Usaha Kantor Akuntan Publik Dalam Rangka Audit Kinerja Lembaga Kebijakan Pengadaan Barang/Jasa Pemerintah");
//            project.setDescription("");
//            project.setStatus(Project.Status.COMPLETE);
//            project.setCategory("Other services");
//            project.setProcurementMethod(Project.ProcurementMethod.AUCTION);
//            project.setDocumentationMethod(Project.DocumentationMethod.SINGLE);
//            project.setQualificationMethod(Project.QualificationMethod.POST);
//            project.setEvaluationMethod(Project.EvaluationMethod.KNOCKOUT);
//            project.setBudgetReference("2015 - APBN");
//            project.setValue(2_000_000_000D);
//            project.setEstimatedValue(1_665_650_030D);
//            project.setPaymentMethod(Project.PaymentMethod.LUMP_SUM);
//            project.setFiscalYearImposition(Project.FiscalYearImposition.SINGLE);
//            project.setFundingSource(Project.FundingSource.SINGLE);
//            project.setBusinessQualification(Project.BusinessQualification.SMALL);
//            project.setRequirements(new String[] {
//                    "Memiliki Surat Ijin Usaha/SIUP di bidang jasa pengelolaan gedung, jasa untuk gedung dan pertamanan, atau sejenis yang masih berlaku;",
//                    "Telah melunasi kewajiban pajak tahun terakhir (SPT/PPh)memiliki Nomor Pokok Wajib Pajak (NPWP) dan telah memenuhi kewajiban perpajakan tahun terakhir (SPT Tahunan) Tahun 2014;",
//                    "salah satu dan/atau semua pengurus dan badan usahanya atau peserta perorangan tidak masuk dalam Daftar Hitam;",
//                    "memperoleh paling sedikit 1 (satu) pekerjaan sebagai penyedia dalam kurun waktu 4 (empat) tahun terakhir, baik di lingkungan pemerintah maupun swasta termasuk pengalaman subkontrak, kecuali bagi Penyedia yang baru berdiri kurang dari 3 (tiga) tahun;",
//                    "memiliki pengalaman di bidang pekerjaan yang sejenis dalam kurun waktu 10 tahun terakhir.",
//                    "Menyediakan peralatan sesuai dengan LDK",
//                    "Memiliki Tenaga Ahli/Terampil, dengan kualifikasi sesuai dengan LDK"
//            });
//            project.setLocation(Location.fromJson("{\"results\":[{\"address_components\":[{\"long_name\":\"12780\",\"short_name\":\"12780\",\"types\":[\"postal_code\"]},{\"long_name\":\"Pancoran\",\"short_name\":\"Pancoran\",\"types\":[\"administrative_area_level_4\",\"political\"]},{\"long_name\":\"Pancoran\",\"short_name\":\"Pancoran\",\"types\":[\"administrative_area_level_3\",\"political\"]},{\"long_name\":\"South Jakarta City\",\"short_name\":\"Kota Jakarta Selatan\",\"types\":[\"administrative_area_level_2\",\"political\"]},{\"long_name\":\"Special Capital Region of Jakarta\",\"short_name\":\"Special Capital Region of Jakarta\",\"types\":[\"administrative_area_level_1\",\"political\"]},{\"long_name\":\"Indonesia\",\"short_name\":\"ID\",\"types\":[\"country\",\"political\"]}],\"formatted_address\":\"Pancoran, Pancoran, South Jakarta City, Special Capital Region of Jakarta 12780, Indonesia\",\"geometry\":{\"bounds\":{\"northeast\":{\"lat\":-6.2408261,\"lng\":106.847168},\"southwest\":{\"lat\":-6.253495600000001,\"lng\":106.8348464}},\"location\":{\"lat\":-6.2461535,\"lng\":106.8399623},\"location_type\":\"APPROXIMATE\",\"viewport\":{\"northeast\":{\"lat\":-6.2408261,\"lng\":106.847168},\"southwest\":{\"lat\":-6.253495600000001,\"lng\":106.8348464}}},\"partial_match\":true,\"place_id\":\"ChIJfQnaELHzaS4RYDYDTevFABw\",\"types\":[\"postal_code\"]}],\"status\":\"OK\"}"));
//            System.out.println(this.projects.save(project));
//            for (Project project: this.projects.findAll()) {
//                System.out.println(project);
//            }

            Bid bid = new Bid();
            bid.setAdministrativeCompleteness(true);
            bid.setAdjustedPrice(10D);
            bid.setPrice(9D);
            bid.setReason("reason");
            bid.setScore(9.9D);
            bid.setTechnicalCompleteness(true);
            bid.setWinningBid(true);
            bid = this.bids.save(bid);

            Vendor vendor = new Vendor();
            vendor.setName("name");
            vendor.setBusinessRegistrationId("id");
            vendor = this.vendors.save(vendor);

            Representative rep = new Representative();
            rep.setName("rep");
            rep = this.representatives.save(rep);

            this.bids.bid(vendor, this.projects.findOne(1L), bid, rep);

            Project project = this.neo4jTemplate.load(Project.class, 1L, 2);
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
