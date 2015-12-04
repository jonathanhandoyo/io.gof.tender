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

import java.util.List;
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
    public @ResponseBody ResponseEntity<?> all() {
        try {
            return new ResponseEntity<>(StreamSupport.stream(this.projects.findAll(1).spliterator(), false).collect(Collectors.toSet()), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "near/{lng}/{lat}/distance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> near(Double lng, Double lat, Double distance) {
        try {

            List<ProjectLocation> projectLocations = this.projectLocations.findByCoordinateNear(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS));

            List<Project> projects = null;

            return new ResponseEntity<>(projects, HttpStatus.OK);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "within/{swLng}/{swLat}/{neLng}/{neLat}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> within(Double swLng, Double swLat, Double neLng, Double neLat) {
        try {

            List<ProjectLocation> projectLocations = this.projectLocations.findByCoordinateWithin(new Box(new Point(swLng, swLat), new Point(neLng, neLat)));

            List<Project> projects = null;

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "within/{lng}/{lat}/distance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> within(Double lng, Double lat, Integer distance) {
        try {

            List<ProjectLocation> projectLocations = this.projectLocations.findByCoordinateWithin(new Circle(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS)));

            List<Project> projects = null;

            return new ResponseEntity<>(projects, HttpStatus.OK);
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
