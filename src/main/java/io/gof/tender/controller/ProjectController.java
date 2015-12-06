package io.gof.tender.controller;

import com.sun.xml.internal.bind.v2.model.core.ID;
import io.gof.tender.domain.Location;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.LocationRepository;
import io.gof.tender.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> all(@RequestParam(required = false) String[] ids) {
        try {
            if (ids != null && ids.length > 0) {
                return new ResponseEntity<>(StreamSupport.stream(this.projects.findAll(Arrays.asList(ids)).spliterator(), true).collect(Collectors.toSet()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(StreamSupport.stream(this.projects.findAll().spliterator(), true).collect(Collectors.toSet()), HttpStatus.OK);
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

                Iterable<String> projectSet = StreamSupport.stream(projectLocations.spliterator(), true)
                        .map(Location::getProject)
                        .map(Project::getId)
                        .collect(Collectors.toSet());
                Iterable<Project> projects = this.projects.findAll(projectSet);

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

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> get(@PathVariable String id) {
        try {
            return new ResponseEntity<>(this.projects.findOne(id), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> update(@PathVariable String id, @RequestBody Project project) {
        try {
            return new ResponseEntity<>(this.projects.findOne(id), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
