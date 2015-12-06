package io.gof.tender.repository;

import io.gof.tender.domain.Project;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {
    @Query("{location: {$exists:true}}")
    Stream<Project> findAllWithLocationExists();
}
