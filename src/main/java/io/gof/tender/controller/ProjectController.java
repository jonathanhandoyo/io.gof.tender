package io.gof.tender.controller;

import io.gof.tender.domain.Comment;
import io.gof.tender.domain.Location;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.CommentRepository;
import io.gof.tender.repository.LocationRepository;
import io.gof.tender.repository.ProjectRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectRepository projects;

    @Autowired
    private CommentRepository comments;

    @Autowired
    private LocationRepository projectLocations;

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

//    @RequestMapping(value = "/near/{lng}/{lat}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody ResponseEntity<?> near(@PathVariable Double lng, @PathVariable Double lat, @PathVariable Double distance) {
//        try {
//
//            Iterable<Location> projectLocations = this.projectLocations.findByCoordinateNear(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS));
//
//            if (projectLocations != null) {
//
//                Iterable<Project> projects = this.projects.findAll(StreamSupport.stream(projectLocations.spliterator(), true).map(Location::getProjectId).collect(Collectors.toSet()), 1);
//
//                return new ResponseEntity<>(StreamSupport.stream(projects.spliterator(), true).collect(Collectors.toList()), HttpStatus.OK);
//            }
//
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

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

//    @RequestMapping(value = "within/{lng}/{lat}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody ResponseEntity<?> within(@PathVariable Double lng, @PathVariable Double lat, @PathVariable Double distance) {
//        try {
//
//            Iterable<Location> projectLocations = this.projectLocations.findByCoordinateWithin(new Circle(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS)));
//
//            if (projectLocations != null) {
//
//                Iterable<Project> projects = this.projects.findAll(StreamSupport.stream(projectLocations.spliterator(), true).map(Location::getProjectId).collect(Collectors.toSet()), 1);
//
//                return new ResponseEntity<>(StreamSupport.stream(projects.spliterator(), true).collect(Collectors.toList()), HttpStatus.OK);
//            }
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> get(@PathVariable String id) {
        try {
            return new ResponseEntity<>(this.projects.findOne(id), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fake", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> fake() {
        try {
            return new ResponseEntity<>(this.projects.findAll(Arrays.asList("56638e769cae775d1452f891","56638e769cae775d1452f893","56638e769cae775d1452f894","56638e769cae775d1452f896","56638e769cae775d1452f897","56638e769cae775d1452f898","56638e769cae775d1452f899","56638e769cae775d1452f89a","56638e769cae775d1452f89b","56638e769cae775d1452f89c","56638e769cae775d1452f89e","56638e769cae775d1452f89f","56638e769cae775d1452f8a0","56638e769cae775d1452f8a1","56638e769cae775d1452f8a2","56638e769cae775d1452f8a3","56638e769cae775d1452f8a4","56638e769cae775d1452f8a5","56638e769cae775d1452f8a7","56638e769cae775d1452f8a8","56638e769cae775d1452f8a9","56638e769cae775d1452f8aa","56638e769cae775d1452f8ab","56638e769cae775d1452f8ac","56638e769cae775d1452f8ad","56638e769cae775d1452f8ae","56638e769cae775d1452f8b0","56638e769cae775d1452f8b2","56638e769cae775d1452f8b3","56638e769cae775d1452f8b4","56638e769cae775d1452f8b5","56638e769cae775d1452f8b7","56638e769cae775d1452f8b8","56638e769cae775d1452f8b9","56638e769cae775d1452f8ba","56638e769cae775d1452f8bb","56638e769cae775d1452f8bc","56638e769cae775d1452f8bd","56638e769cae775d1452f8be","56638e769cae775d1452f8bf","56638e769cae775d1452f8c1","56638e769cae775d1452f8c3","56638e769cae775d1452f8c5","56638e769cae775d1452f8c6","56638e769cae775d1452f8c7","56638e769cae775d1452f8c8")), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{project}/comment/add/{username}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> fake(@PathVariable String projectId, @PathVariable String username, @RequestParam String content) {
        try {
            Project project = this.projects.findOne(projectId);
            project.setComments(ArrayUtils.add(project.getComments(), this.comments.save(Comment.builder()
                            .username(username)
                            .timestamp(new Date())
                            .content(content)
                            .build()
            )));
            return new ResponseEntity<>(this.projects.save(project), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
