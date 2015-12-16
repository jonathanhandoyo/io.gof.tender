package io.gof.tender.controller;

import io.gof.tender.domain.Location;
import io.gof.tender.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/projects/location")
public class ProjectLocationController {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectLocationController.class);

    @Autowired
    LocationRepository locations;

    @RequestMapping(value = "/near/{lng}/{lat}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> near(@PathVariable Double lng, @PathVariable Double lat, @PathVariable Double distance) {
        try {

            Set<Location> projectLocations = this.locations.findByCoordinateNear(new Point(lat, lng), new Distance(distance, Metrics.KILOMETERS));

            return new ResponseEntity<>(projectLocations, HttpStatus.OK);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "within", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> within(@RequestParam Double swLng, @RequestParam Double swLat, @RequestParam Double neLng, @RequestParam Double neLat) {
        try {

            Set<Location> projectLocations = this.locations.findByCoordinateWithin(new Box(new Point(swLat, swLng), new Point(neLat, neLng)));

            return new ResponseEntity<>(projectLocations, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "within/{lng}/{lat}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> within(@PathVariable Double lng, @PathVariable Double lat, @PathVariable Double distance) {
        try {

            Set<Location> projectLocations = this.locations.findByCoordinateWithin(new Circle(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS)));

            return new ResponseEntity<>(projectLocations, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
