package io.gof.tender.repository;

import io.gof.tender.domain.Revision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevisionRepository extends CrudRepository<Revision, String> {
}
