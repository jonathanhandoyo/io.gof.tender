package io.gof.tender.repository;

import io.gof.tender.domain.Location;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
    Set<Location> findByCoordinateWithin(Circle circle);
    Set<Location> findByCoordinateWithin(Box box);
    Set<Location> findByCoordinateWithin(Polygon polygon);
    Set<Location> findByCoordinateNear(Point point, Distance distance);
}