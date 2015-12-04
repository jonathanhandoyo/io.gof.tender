package io.gof.tender.controller;

import io.gof.tender.domain.Project;
import io.gof.tender.domain.ProjectLocation;
import io.gof.tender.domain.User;
import io.gof.tender.domain.Vote;
import io.gof.tender.repository.ProjectLocationRepository;
import io.gof.tender.repository.ProjectRepository;
import io.gof.tender.repository.UserRepository;
import io.gof.tender.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectRepository projects;

    @Autowired
    private ProjectLocationRepository projectLocations;

    @Autowired
    private UserRepository users;

    @Autowired
    private VoteRepository votes;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> all(@RequestParam(required = false) Long[] ids) {
        try {
            if (ids != null && ids.length > 0) {
                return new ResponseEntity<>(StreamSupport.stream(this.projects.findAll(Arrays.asList(ids), 1).spliterator(), false).collect(Collectors.toSet()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(StreamSupport.stream(this.projects.findAll(1).spliterator(), false).collect(Collectors.toSet()), HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "near/{lng}/{lat}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> near(@PathVariable Double lng, @PathVariable Double lat, @PathVariable Double distance) {
        try {

            Iterable<ProjectLocation> projectLocations = this.projectLocations.findByCoordinateNear(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS));

            if (projectLocations != null) {

                Iterable<Project> projects = this.projects.findAll(StreamSupport.stream(projectLocations.spliterator(), true).map(ProjectLocation::getProjectId).collect(Collectors.toSet()), 1);

                return new ResponseEntity<>(StreamSupport.stream(projects.spliterator(), true).collect(Collectors.toList()), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "within/{swLng}/{swLat}/{neLng}/{neLat}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> within(@PathVariable Double swLng, @PathVariable Double swLat, @PathVariable Double neLng, @PathVariable Double neLat) {
        try {

            Iterable<ProjectLocation> projectLocations = this.projectLocations.findByCoordinateWithin(new Box(new Point(swLng, swLat), new Point(neLng, neLat)));

            if (projectLocations != null) {

                Iterable<Project> projects = this.projects.findAll(StreamSupport.stream(projectLocations.spliterator(), true).map(ProjectLocation::getProjectId).collect(Collectors.toSet()), 1);

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

            Iterable<ProjectLocation> projectLocations = this.projectLocations.findByCoordinateWithin(new Circle(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS)));

            if (projectLocations != null) {

                Iterable<Project> projects = this.projects.findAll(StreamSupport.stream(projectLocations.spliterator(), true).map(ProjectLocation::getProjectId).collect(Collectors.toSet()), 1);

                return new ResponseEntity<>(StreamSupport.stream(projects.spliterator(), true).collect(Collectors.toList()), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.projects.findOne(id, 2), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{projectId}/vote/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> get(@PathVariable Long projectId, @PathVariable Long userId) {
        try {
            Project project = this.projects.findOne(projectId);
            User user = this.users.findOne(userId);

            Vote vote = this.votes.vote(project, user);
            return new ResponseEntity<>(vote, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
