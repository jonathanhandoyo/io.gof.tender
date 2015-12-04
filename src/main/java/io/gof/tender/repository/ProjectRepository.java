package io.gof.tender.repository;

import io.gof.tender.domain.Project;
import io.gof.tender.domain.Vendor;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends GraphRepository<Project> {
    @Query( " MATCH (project:Project) " +
            " WHERE project.code = {0} " +
            "RETURN project; ")
    Project findByCode(String code);

    @Query( " MATCH (project:Project), (vendor:Vendor) " +
            " WHERE id(project) = {0} " +
            "   AND id(vendor)  = {1} " +
            "CREATE UNIQUE (project) -[:APPOINTED_TO]-> (vendor); ")
    Project setWinner(Project project, Vendor vendor);
}
