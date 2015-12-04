package io.gof.tender.repository;

import io.gof.tender.domain.Project;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends GraphRepository<Project> {
    @Query( " MATCH (project:Project) " +
            " WHERE project.code = {0} " +
            "RETURN project; ")
    Project findByCode(String code);
}
