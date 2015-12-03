package io.gof.tender.test;

import io.gof.tender.Application;
import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.domain.Milestone;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.BidRepository;
import io.gof.tender.repository.ProjectRepository;
import io.gof.tender.repository.RepresentativeRepository;
import io.gof.tender.repository.VendorRepository;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.ArrayUtils;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.gof.tender.util.CustomMap.map;
import static org.apache.commons.lang3.tuple.Pair.of;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@WebAppConfiguration
public class DataBootstrapper {
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
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setCurrencySymbol("Rp ");
        symbols.setMonetaryDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat format = new DecimalFormat("¤###,###.00", symbols);

        Reader in = new FileReader("C:\\git\\io.gof.tender\\src\\test\\resources\\data_v1.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        for (CSVRecord record : records) {
            System.out.println(record.get("tahap_lelang/_text"));

            Project project = new Project(
                record.get("nama_lelang"),
                Project.Status.COMPLETE,
                record.get("kategori"),
                "",
                new Project.Methodology(
                        record.get("metode_dokumen"),
                        record.get("metode_evaluasi"),
                        record.get("metode_pengadaan"),
                        record.get("metode_kualifikasi")
                ),
                new Project.Valuation(format.parse(record.get("nilai_pagu").replace("\"", "")).doubleValue(), format.parse(record.get("hps").replace("\"", "")).doubleValue()),
                new Project.Contract(
                        record.get("cara_pembayaran"),
                        record.get("pembebanan_tahun_anggaran"),
                        record.get("sumber_pendanaan")
                ),
                record.get("anggaran"),
                record.get("kualifikasi_usaha"),
                map(
//                        of("weight one", 10D),
//                        of("weight two", 30D),
//                        of("weight three", 60D)
                ),
                null,
                new Project.Requirement(
                        new Project.Requirement.Permit[]{
                                new Project.Requirement.Permit(
                                        "project requirement permit type 1",
                                        "project requirement permit classification 1"
                                ),
                                new Project.Requirement.Permit(
                                        "project requirement permit type 2",
                                        "project requirement permit classification 2"
                                )
                        },
                        new String[] {
                                "project requirement item 1",
                                "project requirement item 2"
                        }
                ),
                null,
                null,
                null,
                null
            );
            System.out.println(project);
        }
    }

    public static void main(String[] args) throws Exception {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setCurrencySymbol("Rp ");
        symbols.setMonetaryDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat format = new DecimalFormat("¤###,###.00", symbols);

        System.out.println(format.parse("\"Rp 1.044.000.000,00\"".replace("\"", "")).toString());
//        System.out.println(format.format(1044000000).toString());
//        Rp 1.044.000.000,00
    }
}
