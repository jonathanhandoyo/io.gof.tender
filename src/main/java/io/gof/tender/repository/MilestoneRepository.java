package io.gof.tender.repository;

import io.gof.tender.domain.Comment;
import io.gof.tender.domain.Milestone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneRepository extends CrudRepository<Milestone, String> {
}
