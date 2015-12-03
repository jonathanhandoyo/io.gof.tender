package io.gof.tender.test;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocoderRequest;
import io.gof.tender.Application;
import io.gof.tender.config.ApplicationConfiguration;
import io.gof.tender.domain.Document;
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
import org.apache.commons.lang3.StringUtils;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    private Geocoder geocoder;

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

        Reader in = new FileReader("C:\\git\\io.gof.tender\\src\\test\\resources\\data_projects_v1.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        for (CSVRecord record : records) {
            try {

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
                        new Document[0],
                        record.get("anggaran"),
                        record.get("kualifikasi_usaha"),
                        map(
//                            of("weight one", 10D),
//                            of("weight two", 30D),
//                            of("weight three", 60D)
                        ),
                        record.get("lokasi_pekerjaan") != null
//                                ? this.geocoder.geocode(new GeocoderRequestBuilder().setAddress(record.get("lokasi_pekerjaan")).setLanguage("en").getGeocoderRequest())
                                ? null
                                : null,
                        record.get("lokasi_pekerjaan"),
                        new Project.Requirement(
                                new Project.Requirement.Permit[0],
//                            new Project.Requirement.Permit[]{
//                                    new Project.Requirement.Permit(
//                                            "project requirement permit type 1",
//                                            "project requirement permit classification 1"
//                                    ),
//                                    new Project.Requirement.Permit(
//                                            "project requirement permit type 2",
//                                            "project requirement permit classification 2"
//                                    )
//                            },
                                Arrays.stream(StringUtils.split(record.get("syarat_kualifikasi"), "*")).filter(it -> !"Ijin Usaha Ijin Usaha Klasifikasi".equals(it.trim())).toArray(String[]::new)
                        ),
                        null,
                        null,
                        null,
                        null,
                        null
                );
                System.out.println(project);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String seed = "* Ijin Usaha Ijin Usaha Klasifikasi * Telah melunasi kewajiban pajak tahun terakhir (SPT/PPh) memiliki Nomor Pokok Wajib Pajak (NPWP) dan telah memenuhi kewajiban perpajakan tahun terakhir (SPT Tahunan) serta memiliki laporan bulanan PPh Pasal 21, PPh Pasal 23 (bila ada transaksi), PPh Pasal 25/Pasal 29 dan PPN (bagi Pengusaha Kena Pajak) paling kurang 3 (tiga) bulan terakhir, yaitu Agustus 2013, September 2013 dan Oktober 2013. Peserta dapat mengganti persyaratan ini dengan menyampaikan Surat Keterangan Fiskal (SKF) yang dikeluarkan oleh Kantor Pelayanan Pajak dengan tanggal penerbitan paling lama 1 (satu) bulan sebelum tanggal mulai pemasukan Dokumen Kualifikasi * memiliki pengalaman dalam penyediaan sistem dan infrastruktur Call Center beserta maintenance minimal 3 tahun * memiliki Tenaga Teknis/Terampil untuk melaksanakan pekerjaan ini * memiliki kemampuan untuk menyediakan fasilitas/peralatan/perlengkapan untuk melaksanakan pekerjaan ini";
//        Arrays.asList(StringUtils.split(seed, "*")).stream().forEach(it -> System.out.println(" - " + it.trim()));
        String[] test = Arrays.stream(StringUtils.split(seed, "*")).filter(it -> !"Ijin Usaha Ijin Usaha Klasifikasi".equals(it.trim())).toArray(String[]::new);
        System.out.println(test);
    }
}
