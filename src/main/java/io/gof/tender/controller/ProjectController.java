package io.gof.tender.controller;

import com.sun.xml.internal.bind.v2.model.core.ID;
import io.gof.tender.domain.Location;
import io.gof.tender.domain.Project;
import io.gof.tender.repository.LocationRepository;
import io.gof.tender.repository.ProjectRepository;
import org.apache.commons.lang3.ArrayUtils;
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

    @RequestMapping(value = "/fake", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> fake(@PathVariable String id) {
        try {
            return new ResponseEntity<>(this.projects.findAll(Arrays.asList("5662c7f0ceea2b2e62f038c4","5662c7f2ceea2b2e62f038c7","5662c805ceea2b2e62f038d4","5662c806ceea2b2e62f038d6","5662c80aceea2b2e62f038db","5662c80cceea2b2e62f038dd","5662c80eceea2b2e62f038df","5662c813ceea2b2e62f038e6","5662c815ceea2b2e62f038e8","5662c817ceea2b2e62f038eb","5662c821ceea2b2e62f038f3","5662c822ceea2b2e62f038f5","5662c823ceea2b2e62f038f6","5662c825ceea2b2e62f038f8","5662c826ceea2b2e62f038fa","5662c82cceea2b2e62f03901","5662c82dceea2b2e62f03902","5662c82eceea2b2e62f03903","5662c834ceea2b2e62f0390b","5662c835ceea2b2e62f0390d","5662c837ceea2b2e62f0390e","5662c83aceea2b2e62f03913","5662c83dceea2b2e62f03916","5662c840ceea2b2e62f03919","5662c841ceea2b2e62f0391a","5662c843ceea2b2e62f0391d","5662c844ceea2b2e62f0391e","5662c845ceea2b2e62f03920","5662c847ceea2b2e62f03921","5662c848ceea2b2e62f03922","5662c84eceea2b2e62f0392a","5662c857ceea2b2e62f0392c","5662c85cceea2b2e62f03932","5662c85cceea2b2e62f03933","5662c7edceea2b2e62f038c0","5662c7faceea2b2e62f038cd","5662c7fcceea2b2e62f038cf","5662c806ceea2b2e62f038d5","5662c80aceea2b2e62f038da","5662c816ceea2b2e62f038ea","5662c827ceea2b2e62f038fc","5662c830ceea2b2e62f03905","5662c83bceea2b2e62f03914","5662c83fceea2b2e62f03918","5662c842ceea2b2e62f0391c","5662c84dceea2b2e62f03929")), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
