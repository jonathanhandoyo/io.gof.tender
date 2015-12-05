package io.gof.tender.repository;

import io.gof.tender.domain.Location;
import org.springframework.data.geo.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, String> {
    Iterable<Location> findByCoordinateWithin(Circle circle);
    Iterable<Location> findByCoordinateWithin(Box box);
    Iterable<Location> findByCoordinateWithin(Polygon polygon);
    Iterable<Location> findByCoordinateNear(Point point, Distance distance);
}