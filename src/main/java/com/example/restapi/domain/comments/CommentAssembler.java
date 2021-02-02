package com.example.restapi.domain.comments;


import com.example.restapi.web.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentAssembler extends RepresentationModelAssemblerSupport<CommentResponseDto, CommentModel> {


    public CommentAssembler() {
        super(CommentsController.class, CommentModel.class);
    }

    @Override
    public CommentModel toModel(CommentResponseDto entity) {
        return  CommentModel.builder()
                .id(entity.getId())
                .postId(entity.getPostId())
                .content(entity.getContent())
                .userEmail(entity.getUserEmail())
                .userNickName(entity.getUserNickName())
                .modifyDate(entity.getModifyDate())
                .isWriter(entity.getIsWriter())
                .build()
                .add(linkTo(
                        methodOn(CommentsController.class).findById(entity.getPostId() ,entity.getId(), null)).withSelfRel());
    }

    @Override
    public CollectionModel<CommentModel> toCollectionModel(Iterable<? extends CommentResponseDto> entities)
    {
        CollectionModel<CommentModel> models = super.toCollectionModel(entities);

        return models;
    }

}
