package io.gof.tender.controller;

import io.gof.tender.domain.Document;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.ProjectRepository;
import io.gof.tender.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import static io.gof.tender.util.CustomMap.map;

@RestController
@RequestMapping("/tests")
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserRepository users;

    @Autowired
    private ProjectRepository projects;

    @RequestMapping(value = "/case1", method = RequestMethod.GET)
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
                project = this.projects.save(project);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
