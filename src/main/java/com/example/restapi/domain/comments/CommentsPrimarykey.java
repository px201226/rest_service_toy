package com.example.restapi.domain.comments;


import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class CommentsPrimarykey implements Serializable {

    @NotNull
    private Long postId;

    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
}
