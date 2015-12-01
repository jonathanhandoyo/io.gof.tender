package io.gof.tender.repository;

import io.gof.tender.domain.Representative;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativeRepository extends GraphRepository<Representative> {
}
