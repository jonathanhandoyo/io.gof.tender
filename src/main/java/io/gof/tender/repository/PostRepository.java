package io.gof.tender.repository;

import io.gof.tender.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, String> {
    List<Post> findAllByProjectIdOrderByDateDesc(String projectId);
}