package io.gof.tender.repository;

import io.gof.tender.domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
    List<Comment> findAllByProjectIdOrderByTimestampDesc(String projectId);
}
