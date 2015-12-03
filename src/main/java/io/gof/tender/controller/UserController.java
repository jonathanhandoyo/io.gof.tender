package io.gof.tender.controller;

import io.gof.tender.domain.User;
import io.gof.tender.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository users;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            User test = this.users.login(username, password);
            if (test == null) {
                return new ResponseEntity<>(username, HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(test, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
