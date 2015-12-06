package io.gof.tender.test;

import io.gof.tender.BaseTester;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.ProjectRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class MainTest extends BaseTester {
    @Autowired
    private ProjectRepository projects;

    @Autowired
    private MongoTemplate mongoTemplate;

    static SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    @Test
    public void test1() throws Exception {
        Project.Album album = new Project.Album();
        album.setImages(new Project.Album.Image[]{
                new Project.Album.Image("Image 1",null,null,"image1.jpg","image1.jpg","Image 1"),
                new Project.Album.Image("Image 3",null,null,"image2.jpg","image2.jpg","Image 3"),
                new Project.Album.Image("Image 2",null,null,"image3.jpg","image3.jpg","Image 2")
        });

        Project.Milestone milestone1 = new Project.Milestone();
        milestone1.setTitle("Project first milestone");
        milestone1.setContent("Lorem ipsum dolor sit amet consiquest dio Lorem ipsum dolor sit amet consiquest dio");
        milestone1.setDue(SDF.parse("02-06-2016"));
        mongoTemplate.save(milestone1);

        Project.Milestone milestone2 = new Project.Milestone();
        milestone2.setTitle("Project second milestone");
        milestone2.setContent("<a href=\"#\">Lorem ipsum</a> dolor sit amet consiquest dio Lorem ipsum dolor sit amet <span><a href=\"#\" class=\"blue\">consiquest dio</a></span>");
        milestone2.setDue(SDF.parse("05-08-2016"));
        milestone2.setAlbum(album);
        mongoTemplate.save(milestone2);


        Project.Milestone milestone3 = new Project.Milestone();
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
                project.setMilestones(new Project.Milestone[] {
                    milestone1,
                    milestone2,
                    milestone3
                });

                projects.save(project);

                System.out.println(project);
            });
        }
    }
}
