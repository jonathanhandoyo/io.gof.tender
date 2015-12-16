package io.gof.tender.repository;

import io.gof.tender.domain.Location;
import io.gof.tender.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {
    /*@Query(value = "{locations: {&in:true}}",
            fields = "{name:1, location:1, category:1, announcementDate:1, biddingEndDate:1 }")
    Page<Project> findAllWithLocationsExists(Pageable pageable);

    @Query("{location: {$exists:false}}")
    Page<Project> findWithoutLocations(Pageable pageable);

    @Query(fields = "{name:1, location:1, category:1, announcementDate:1, biddingEndDate:1 }")
    List<Project> findAllByLocationIn(Set<Location> locationSet);*/
}
