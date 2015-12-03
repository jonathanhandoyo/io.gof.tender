package io.gof.tender.repository;

import io.gof.tender.domain.PersistentToken;
import io.gof.tender.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface PersistentTokenRepository extends GraphRepository<PersistentToken> {
    @Query( " MATCH (token:PersistentToken) --> (user:User)" +
            " WHERE id(user) = {0} " +
            "RETURN token; ")
    List<PersistentToken> findByUser(User user);

    @Query( " MATCH (token:PersistentToken)" +
            " WHERE token.tokenDate < {0} " +
            "RETURN token; ")
    List<PersistentToken> findByTokenDateBefore(Long timestamp);

    @Query( " MATCH (persistentToken:PersistentToken) " +
            " WHERE persistentToken.series = {0}" +
            "RETURN persistentToken; ")
    PersistentToken findByOneBySeries(String presentedSeries);
}
