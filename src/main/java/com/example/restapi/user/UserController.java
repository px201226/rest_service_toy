package com.example.restapi.user;

import com.example.restapi.domain.user.User;
import com.example.restapi.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService){
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User>  retrieveUser(@PathVariable Integer id){
        User user = userDaoService.findById(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(
                methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User save = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        User user = userDaoService.deleteById(id);
        if( user == null)
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
    }

    @PutMapping("/users/{id}")
    public User modified(@PathVariable Integer id, @RequestBody User user){
        User modifyUser = userDaoService.findById(id);
        if(modifyUser == null)
            throw new UserNotFoundException(String.format("ID[%s] not found", id));

        return userDaoService.modifyById(id, user);
    }
}
