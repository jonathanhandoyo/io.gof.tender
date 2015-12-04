package io.gof.tender.repository;

import io.gof.tender.domain.ProjectLocation;
import org.springframework.data.geo.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectLocationRepository extends CrudRepository<ProjectLocation, String> {
    List<ProjectLocation> findByCoordinateWithin(Circle circle);
    List<ProjectLocation> findByCoordinateWithin(Box box);
    List<ProjectLocation> findByCoordinateWithin(Polygon polygon);
    List<ProjectLocation> findByCoordinateNear(Point point, Distance distance);
}