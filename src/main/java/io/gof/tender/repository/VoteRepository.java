package io.gof.tender.repository;

import io.gof.tender.domain.Project;
import io.gof.tender.domain.User;
import io.gof.tender.domain.Vote;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends GraphRepository<Vote> {
    @Query( " MATCH (project:Project) <-- (vote:Vote) " +
            " WHERE id(project) = {0} " +
            "RETURN count(vote); ")
    Long count(Project project);

    @Query( " MATCH (project:Project) <-- (vote:Vote) --> (user:User) " +
            " WHERE id(project) = {0} " +
            "   AND id(user)    = {1} " +
            "RETURN count(vote) > 0; ")
    Boolean validate(Project project, User user);

    @Query( " MERGE (project:Project) <-- (vote:Vote) --> (user:User) " +
            " WHERE id(project) = {0} " +
            "   AND id(user)    = {1} " +
            " ON CREATE SET vote.timestamp = timestamp() ")
    Vote vote(Project project, User user);
}
