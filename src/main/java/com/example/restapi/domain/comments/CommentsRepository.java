package com.example.restapi.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query("SELECT p FROM Comments p ORDER BY p.id DESC")
    List<Comments> findAllDesc();
}
