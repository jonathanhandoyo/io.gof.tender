package io.gof.tender.repository;

import io.gof.tender.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {
    @Query("{location: {$exists:true}} ")
    Page<Project> findAllWithLocationExists(Pageable pageable);

    @Query("{location: {$exists:false}}")
    Page<Project> findWithoutLocation(Pageable pageable);
}
