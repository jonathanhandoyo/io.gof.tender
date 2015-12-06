package io.gof.tender.test;

import io.gof.tender.BaseTester;
import io.gof.tender.domain.*;
import io.gof.tender.util.CustomDate;
import io.gof.tender.util.CustomDouble;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Bootstrapper extends BaseTester {
    static SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    @Test
    public void fillMilestones() {
        Milestone one = this.miletones.save(
                Milestone.builder()
                        .title("First Milestone")
                        .content("Lorem ipsum dolor sit amet consiquest dio Lorem ipsum dolor sit amet consiquest dio\"")
                        .due(CustomDate.toDate("yyyy-MM-dd", "2016-06-01"))
                        .build()
        );

        Milestone two = this.miletones.save(
                Milestone.builder()
                        .title("First Milestone")
                        .content("<a href=\"#\">Lorem ipsum</a> dolor sit amet consiquest dio Lorem ipsum dolor sit amet consiquest dio\"")
                        .due(CustomDate.toDate("yyyy-MM-dd", "2016-06-01"))
                        .album(this.getAlbum())
                        .build()
        );

        Milestone three = this.miletones.save(
                Milestone.builder()
                        .title("First Milestone")
                        .content("<a href=\"#\">Lorem ipsum</a> dolor sit amet consiquest dio Lorem ipsum dolor sit amet consiquest dio\"")
                        .due(CustomDate.toDate("yyyy-MM-dd", "2016-06-01"))
                        .album(this.getAlbum())
                        .highlights(new String[] {
                                "Release Highlight #1",
                                "Release Highlight #2",
                                "Release Highlight #3"
                        })
                        .build()
        );

        try (Stream<Project> result = projects.findAllWithLocationExists()) {

            result.forEach(project -> {
                project.setMilestones(new Milestone[] {one, two, three});
                projects.save(project);
                System.out.println(project);
            });
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

    public Milestone.Album getAlbum() {
        return Milestone.Album.builder()
                .images(new Milestone.Album.Image[]{
                                Milestone.Album.Image.builder()
                                        .title("Image 1")
                                        .source("image1.jpg")
                                        .alt("Image A")
                                        .build(),
                                Milestone.Album.Image.builder()
                                        .title("Image 2")
                                        .source("image2.jpg")
                                        .alt("Image B")
                                        .build()
                        }
                )
                .build();
    }

    @Test
    public void insertLocations() throws Exception {
        Project project = this.projects.findOne("5662c7e7ceea2b2e62f038be");
        Reader in = new FileReader("E:\\workspace\\io.gof.tender\\src\\test\\resources\\data_location.csv");

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        List<Location> locationCollection = new ArrayList<Location>();
        for (CSVRecord item : records) {
            double[] coord = new double[2];
            coord[0] = Double.parseDouble(item.get("coordinate/0"));
            coord[1] = Double.parseDouble(item.get("coordinate/1"));
            Location location = Location.builder()
                    .name(item.get("name"))
                    .coordinate(coord)
                    .build();
            this.locations.save(location);
            locationCollection.add(location);
        }
        Random ran = new Random();
        int min = 0;
        int max = 45;
        int x = ran.nextInt((max - min) + 1) + min;

        project.setLocation(locationCollection.get(x));
        this.projects.save(project);
        System.out.println(project);

    }

    @Test
    public void findAndUpdateProject() throws Exception {
        System.out.println("Start read");
        Iterable<Location> locations = this.locations.findAll();

        // Keep that in a constant if it stays the same
        PageRequest request = new PageRequest(0, 46, new Sort(Sort.Direction.DESC, "created"));
        List<Project> projectList = this.projects.findWithoutLocation(request).getContent();

        List<Location> locationCollection = StreamSupport.stream(locations.spliterator(), true).collect(Collectors.toList());

        System.out.println("Total projects " + projectList.size());
        System.out.println("Total locations " + locationCollection.size());

        System.out.println("Starting insertiom");

        for (int i = 0; i < projectList.size(); i++) {
            Project project = projectList.get(i);
            Location location = locationCollection.get(i);
            project.setLocation(location);
            location.setProject(project);
            this.projects.save(project);
            this.locations.save(location);
        }
    }
}
