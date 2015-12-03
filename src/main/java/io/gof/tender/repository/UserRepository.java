package io.gof.tender.repository;

import io.gof.tender.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GraphRepository<User> {
    @Query( " MATCH (user:User) " +
            " WHERE user.username = {username}" +
            "   AND user.password = {password} " +
            "RETURN user; ")
    User login(String username, String password);
}
