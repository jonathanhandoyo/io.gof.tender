package io.gof.tender.controller;

import io.gof.tender.domain.Project;
import io.gof.tender.domain.User;
import io.gof.tender.domain.Vote;
import io.gof.tender.repository.ProjectRepository;
import io.gof.tender.repository.UserRepository;
import io.gof.tender.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectRepository projects;

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
