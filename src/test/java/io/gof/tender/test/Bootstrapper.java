package io.gof.tender.test;

import io.gof.tender.BaseTester;
import io.gof.tender.domain.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

import static io.gof.tender.util.CustomMap.map;

public class Bootstrapper extends BaseTester {
    @Test
    public void fill() throws Exception {
//        this.wipeUsers();
//        this.wipeProjects();
//        this.wipeBids();

//        this.fillUsers();
//        this.fillProjects();
        this.fillBids();
    }

    private void wipeUsers() throws Exception {
        this.users.deleteAll();
    }

    private void wipeProjects() throws Exception {
        this.projects.deleteAll();
    }

    private void wipeBids() throws Exception {
        this.bids.deleteAll();
    }

    private void testBids() throws Exception {
        for (Bid bid: this.bids.findAll()) {
            System.out.println(bid);
        }
    }

    private void testUsers() throws Exception {
        for (User user: this.users.findAll()) {
            System.out.println(user);
        }
    }

    private void testProjects() throws Exception {
        for (Project project: this.projects.findAll()) {
            System.out.println(project);
        }
    }

    private void fillUsers() throws Exception {
        this.users.save(new User("admin", "admin", null, null));
        this.users.save(new User("jonathan", "jonathan", "https://drive.google.com/file/d/0Bygmy11KkxT6SmpVeDN3aDE3dHc/view?usp=sharing", null));
        this.users.save(new User("siwananda", "siwananda", null, null));
        this.users.save(new User("arnold", "arnold", null, null));
    }

    private void fillProjects() throws Exception {
//        Project project1 = new Project(
//                "code",
//                "name",
//                Project.Status.COMPLETE,
//                "category",
//                "description",
//                new Project.Methodology(
//                        "documentation",
//                        "evaluation",
//                        "procurement",
//                        "qualification"
//                ),
//                new Project.Valuation(
//                        10d,
//                        9d
//                ),
//                new Project.Contract(
//                        "payment method",
//                        "fiscal year",
//                        "funding source"
//                ),
//                new Document[] {
//                        new Document(
//                                "content type",
//                                "category",
//                                "title",
//                                "filename",
//                                "base64"
//                        )
//                },
//                "budget reference",
//                "business qualification",
//                map(
//                        Pair.of("weight 1", 60d),
//                        Pair.of("weight 2", 40d)
//                ),
//                null,
//                "location",
//                new Project.Requirement(
//                        new Project.Requirement.Permit[]{
//                                new Project.Requirement.Permit(
//                                        "permit type 1",
//                                        "permit classification 1"
//                                )
//                        },
//                        new String[]{
//                                "requirement item 1",
//                                "requirement item 2",
//                                "requirement item 3"
//                        }
//                ),
//                null/*organizer*/,
//                null/*vendor*/,
//                null/*bids*/,
//                null/*milestones*/,
//                null/*comments*/
//        );

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setCurrencySymbol("Rp ");
        symbols.setMonetaryDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat format = new DecimalFormat("¤###,###.00", symbols);

        Reader in = new FileReader("D:\\workspace\\GoF\\io.gof.tender\\src\\test\\resources\\data_projects_v1.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        for (CSVRecord record : records) {
            try {
                Project project = new Project(
                        record.get("kode_lelang"),
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
                                Arrays.stream(StringUtils.split(record.get("syarat_kualifikasi"), "*")).filter(it -> !"Ijin Usaha Ijin Usaha Klasifikasi".equals(it.trim())).map(it -> it.trim()).toArray(String[]::new)
                        ),
                        null,
                        null,
                        null,
                        null,
                        null
                );
                System.out.println(this.projects.save(project).getCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fillBids() throws Exception {
        int i = 0;
        Reader in = new FileReader("D:\\workspace\\GoF\\io.gof.tender\\src\\test\\resources\\data_bids_v1.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        for (CSVRecord record : records) {
            try {
                String vendorId = record.get("nomor_registrasi");
                if (StringUtils.isBlank(vendorId)) continue;

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
                if (org.apache.commons.lang.StringUtils.isBlank(projectCode)) continue;

                Project project = this.projects.findByCode(projectCode);
                if (project == null) continue;

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
