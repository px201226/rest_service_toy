package com.example.restapi.domain.posts;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.util.UriComponents;

import java.util.List;

public class PagedPostAssemblerAdapter extends PagedResourcesAssembler<PostAdapter> {
    public PagedPostAssemblerAdapter(HateoasPageableHandlerMethodArgumentResolver resolver, UriComponents baseUri) {
        super(resolver, baseUri);
    }

    @Override
    public void setForceFirstAndLastRels(boolean forceFirstAndLastRels) {
        super.setForceFirstAndLastRels(forceFirstAndLastRels);
    }

    @Override
    public PagedModel<EntityModel<PostAdapter>> toModel(Page<PostAdapter> entity) {
        return super.toModel(entity);
    }

    @Override
    public PagedModel<EntityModel<PostAdapter>> toModel(Page<PostAdapter> page, Link selfLink) {
        return super.toModel(page, selfLink);
    }

    @Override
    public <R extends RepresentationModel<?>> PagedModel<R> toModel(Page<PostAdapter> page, RepresentationModelAssembler<PostAdapter, R> assembler) {
        PagedModel<R> rs = super.toModel(page, assembler);
        rs.add(Link.of("docs/index.html#resources-post","documentation_url"));
        return rs;
    }

    @Override
    public <R extends RepresentationModel<?>> PagedModel<R> toModel(Page<PostAdapter> page, RepresentationModelAssembler<PostAdapter, R> assembler, Link link) {
        return super.toModel(page, assembler, link);
    }

    @Override
    public PagedModel<?> toEmptyModel(Page<?> page, Class<?> type) {
        return super.toEmptyModel(page, type);
    }

    @Override
    public PagedModel<?> toEmptyModel(Page<?> page, Class<?> type, Link link) {
        return super.toEmptyModel(page, type, link);
    }

    @Override
    protected <R extends RepresentationModel<?>, S> PagedModel<R> createPagedModel(List<R> resources, PagedModel.PageMetadata metadata, Page<S> page) {
        return super.createPagedModel(resources, metadata, page);
    }

    @Override
    protected MethodParameter getMethodParameter() {
        return super.getMethodParameter();
    }
}
