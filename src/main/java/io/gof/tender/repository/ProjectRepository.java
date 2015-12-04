package io.gof.tender.repository;

import io.gof.tender.domain.Project;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends GraphRepository<Project> {
}
