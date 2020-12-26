package com.example.restapi.common;

import com.example.restapi.domain.user.User;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;

public class ResourceProcessor  implements RepresentationModelProcessor<EntityModel<User>> {
    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        return null;
    }
}
