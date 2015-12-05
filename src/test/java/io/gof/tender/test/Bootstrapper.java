package io.gof.tender.test;

import io.gof.tender.BaseTester;
import io.gof.tender.domain.*;
import io.gof.tender.util.CustomDate;
import io.gof.tender.util.CustomDouble;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.util.Date;

public class Bootstrapper extends BaseTester {
    @Test
    public void fillComments() {
        for (Project project: this.projects.findAll()) {
        }
    }

    @Test
    public void fillUsers() throws Exception {
        System.out.println(this.users.save(User.builder().username("jonathan").password("jonathan").build()));
        System.out.println(this.users.save(User.builder().username("arnold").password("arnold").build()));
        System.out.println(this.users.save(User.builder().username("siwananda").password("siwananda").build()));
    }

    @Test
    public void fillProjects() throws Exception {
        Reader in = new FileReader("D:\\workspace\\GoF\\io.gof.tender\\src\\test\\resources\\data_projects_v2.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        for (CSVRecord record : records) {
            try {
                Project project = Project.builder()
                        .code(record.get("kd_paket"))
                        .name(record.get("nama_paket"))
                        .organizer(record.get("nama_panitia"))
                        .announcementDate(CustomDate.toDate("yyyy-MM-dd", record.get("tgl_pengumuman")))
                        .contractValue(CustomDouble.toDouble(record.get("nilai_kontrak")))
                        .contractDate(CustomDate.toDate("yyyy-MM-dd", record.get("nilai_kontrak")))
                        .biddingCode(record.get("kd_lelang"))
                        .biddingEndDate(CustomDate.toDate("yyyy-MM-dd", record.get("tanggal_akhir_lelang")))
                        .fundSource(record.get("sumber_dana"))
                        .fiscalYear(record.get("tahun_anggaran"))
                        .category(record.get("kategori"))
                        .districtCode(record.get("kd_kabupaten"))
                        .districtName(record.get("nama_kabupaten"))
                        .provinceName(record.get("nama_propinsi"))
                        .milestones(null)
                        .location(null)
                        .vendor(this.getVendor(record))
                        .workUnit(Tuple.builder()
                                .code(record.get("kd_satker"))
                                .name(record.get("nama_satker"))
                                .build()
                        )
                        .agency(Tuple.builder()
                                .code(record.get("kd_agency"))
                                .name(record.get("nama_agency"))
                                .build()
                        )
                        .purchaser(Tuple.builder()
                                .code(record.get("kd_lpse"))
                                .name(record.get("nama_lpse"))
                                .build()
                        )
                        .price(Project.Price.builder()
                                .estimated(CustomDouble.toDouble(record.get("hps")))
                                .ceiling(CustomDouble.toDouble(record.get("pagu")))
                                .winning(CustomDouble.toDouble(record.get("hasil_lelang")))
                                .build()
                        )
                        .comments(null)
                        .build();
                System.out.println(this.projects.save(project));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Vendor getVendor(CSVRecord record) {
        Vendor vendor = this.vendors.findByBusinessRegistrationId(record.get("npwp_pemenang"));
        if (vendor != null) {
            return vendor;
        } else {
            return this.vendors.save(
                    Vendor.builder()
                            .code(record.get("kd_pemenang"))
                            .name(record.get("nama_pemenang"))
                            .businessRegistrationId(record.get("npwp_pemenang"))
                            .build()
            );
        }
    }
}
