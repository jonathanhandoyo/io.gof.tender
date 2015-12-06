package io.gof.tender.test;

import io.gof.tender.BaseTester;
import io.gof.tender.domain.Milestone;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.ProjectRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class MainTest extends BaseTester {
    static SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    /*@Test
    public void test1() throws Exception {
        Milestone.Album album = new Milestone.Album();
        album.setImages(new Milestone.Album.Image[]{
                new Milestone.Album.Image("Image 1",null,null,"image1.jpg","image1.jpg","Image 1"),
                new Milestone.Album.Image("Image 3",null,null,"image2.jpg","image2.jpg","Image 3"),
                new Milestone.Album.Image("Image 2",null,null,"image3.jpg","image3.jpg","Image 2")
        });

        Milestone one = Milestone.builder()
                .title("Title of first Mileston")
                .due(SDF.parse("02-06-2016"))
                .content("Lorem ipsum dolor sit amet consiquest dio Lorem ipsum dolor sit amet consiquest dio")
                .album()
                .build();

        Milestone milestone1 = new Milestone();
        milestone1.setTitle("Project first milestone");
        milestone1.setContent("Lorem ipsum dolor sit amet consiquest dio Lorem ipsum dolor sit amet consiquest dio");
        milestone1.setDue(SDF.parse("02-06-2016"));
        mongoTemplate.save(milestone1);

        Milestone milestone2 = new Milestone();
        milestone2.setTitle("Project second milestone");
        milestone2.setContent("<a href=\"#\">Lorem ipsum</a> dolor sit amet consiquest dio Lorem ipsum dolor sit amet <span><a href=\"#\" class=\"blue\">consiquest dio</a></span>");
        milestone2.setDue(SDF.parse("05-08-2016"));
        milestone2.setAlbum(album);
        mongoTemplate.save(milestone2);


        Milestone milestone3 = new Milestone();
        milestone3.setTitle("Project third milestone");
        milestone3.setContent("<a href=\"#\">Lorem ipsum</a> dolor sit amet consiquest dio Lorem ipsum dolor sit amet <span><a href=\"#\" class=\"blue\">consiquest dio</a></span>");
        milestone3.setDue(SDF.parse("05-08-2016"));
        milestone3.setAlbum(album);
        milestone3.setHighlights(new String[]{
            "Release highlight 1",
            "Release highlight 2",
            "Release highlight 3",
        });
        mongoTemplate.save(milestone3);

        try (Stream<Project> result = projects.findAllWithLocationExists()) {

            result.forEach(project -> {
                project.setMilestones(new Milestone[] {
                    milestone1,
                    milestone2,
                    milestone3
                });

                projects.save(project);

                System.out.println(project);
            });
        }
    }*/
}
