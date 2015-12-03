package io.gof.tender.repository;

import io.gof.tender.domain.Project;
import io.gof.tender.domain.User;
import io.gof.tender.domain.Vote;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends GraphRepository<Project> {
}
