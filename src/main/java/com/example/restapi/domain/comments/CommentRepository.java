package com.example.restapi.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT p FROM Comment p ORDER BY p.id DESC")
    List<Comment> findAllDesc();
}
