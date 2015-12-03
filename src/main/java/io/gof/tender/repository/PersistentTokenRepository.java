package io.gof.tender.repository;

import io.gof.tender.domain.PersistentToken;
import io.gof.tender.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface PersistentTokenRepository extends GraphRepository<PersistentToken> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

    @Query( " MATCH (persistentToken:PersistentToken) " +
            " WHERE persistentToken.series = {presentedSeries}" +
            "RETURN persistentToken; ")
    PersistentToken findByOneBySeries(String presentedSeries);
}
