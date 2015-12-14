package io.gof.tender.controller;

import io.gof.tender.domain.Comment;
import io.gof.tender.domain.Location;
import io.gof.tender.domain.Post;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.CommentRepository;
import io.gof.tender.repository.LocationRepository;
import io.gof.tender.repository.PostRepository;
import io.gof.tender.repository.ProjectRepository;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectRepository projects;

    @Autowired
    private LocationRepository projectLocations;

    @Autowired
    private CommentRepository comments;

    @Autowired
    private PostRepository posts;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> all(@RequestParam(required = false) String[] ids) {
        try {
            if (ids != null && ids.length > 0) {
                return new ResponseEntity<>(StreamSupport.stream(this.projects.findAll(Arrays.asList(ids)).spliterator(), true).collect(Collectors.toSet()), HttpStatus.OK);
            } else {
                PageRequest request = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "created"));
                return new ResponseEntity<>(StreamSupport.stream(this.projects.findAllWithLocationExists(request).spliterator(), true).collect(Collectors.toSet()), HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/near/{lng}/{lat}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> near(@PathVariable Double lng, @PathVariable Double lat, @PathVariable Double distance) {
        try {

            Iterable<Location> projectLocations = this.projectLocations.findByCoordinateNear(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS));

            return new ResponseEntity<>(projectLocations, HttpStatus.OK);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "within", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> within(@RequestParam Double swLng, @RequestParam Double swLat, @RequestParam Double neLng, @RequestParam Double neLat) {
        try {

            Iterable<Location> projectLocations = this.projectLocations.findByCoordinateWithin(new Box(new Point(swLng, swLat), new Point(neLng, neLat)));

            if (projectLocations != null) {

                Set<Location> locationSet = StreamSupport.stream(projectLocations.spliterator(), true)
                        .collect(Collectors.toSet());
                Iterable<Project> projects = this.projects.findAllByLocationIn(locationSet);

                return new ResponseEntity<>(StreamSupport.stream(projects.spliterator(), true).collect(Collectors.toList()), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "within/{lng}/{lat}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> within(@PathVariable Double lng, @PathVariable Double lat, @PathVariable Double distance) {
        try {

            Iterable<Location> projectLocations = this.projectLocations.findByCoordinateWithin(new Circle(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS)));

            return new ResponseEntity<>(projectLocations, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> get(@PathVariable String id) {
        try {
            return new ResponseEntity<>(this.projects.findOne(id), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/comment/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> comments(@PathVariable("id") String projectId) {
        try {
            return new ResponseEntity<>(comments.findAllByProjectIdOrderByTimestampDesc(projectId), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/comment/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> addComment(
            @PathVariable("id") String projectId,
            @PathVariable String username,
            @RequestBody String content
    ) {
        try {
            Assert.state(projects.exists(projectId), "Project doesn't exist");

            Comment comment = comments.save(Comment.builder()
                .projectId(projectId)
                .username(username)
                .content(content)
                .timestamp(new Date())
                .build()
            );

            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}/post/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> posts(@PathVariable("id") String projectId) {
        try {
            return new ResponseEntity<>(posts.findAllByProjectIdOrderByDateDesc(projectId), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> addPost(
            @RequestParam String projectId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam MultipartFile image
    ){
        try {
            Assert.state(projects.exists(projectId), "Project doesn't exist");

            String image64 = null;
            if(image != null && !image.isEmpty()){
                image64 = new String(Base64.encodeBase64(image.getBytes()));
            }

            Post post = posts.save(Post.builder()
                .projectId(projectId)
                .username("anonymous")
                .title(title)
                .content(content)
                    .image(Post.Image.builder()
                        .base64(image64)
                        .build())
                .date(new Date())
                .build()
            );

            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
