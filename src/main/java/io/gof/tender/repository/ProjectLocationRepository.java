package io.gof.tender.repository;

import io.gof.tender.domain.ProjectLocation;
import org.springframework.data.geo.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectLocationRepository extends CrudRepository<ProjectLocation, String> {
    Iterable<ProjectLocation> findByCoordinateWithin(Circle circle);
    Iterable<ProjectLocation> findByCoordinateWithin(Box box);
    Iterable<ProjectLocation> findByCoordinateWithin(Polygon polygon);
    Iterable<ProjectLocation> findByCoordinateNear(Point point, Distance distance);
}