//package com.example.restapi.user;
//
//
//import com.example.restapi.domain.user.User;
//import com.example.restapi.domain.user.UserRepository;
//import com.example.restapi.exception.UserNotFoundException;
//import com.example.restapi.post.Post;
//import com.example.restapi.post.PostRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import javax.validation.Valid;
//import java.net.URI;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/jpa")
//public class UserJpaController {
//
//    private final UserRepository userRepository;
//    private final PostRepository postRepository;
//
//    @GetMapping("/users")
//    public List<User> retrieveAllUsers(){
//        return userRepository.findAll();
//    }
//
//    @GetMapping("/users/{id}")
//    public EntityModel<User> retreieveUser(@PathVariable Integer id){
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new UserNotFoundException(String.format("사용자(%d)를 찾을 수 없습니다", id))
//        );
//
//
//        // HATEOAS
//        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(
//                WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
//        EntityModel<User> entityModel = EntityModel.of(user);
//        entityModel.add(webMvcLinkBuilder.withRel("all-users"));
//
//        return entityModel;
//    }
//
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable Integer id){
//        userRepository.deleteById(id);
//    }
//
//    @PostMapping("/users")
//    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
//        User saveUser = userRepository.save(user);
//
//
//        // header에 location 설정...
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(saveUser.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//    @GetMapping("/users/{id}/posts")
//    public List<Post> retrieveAllPostById(@PathVariable Integer id){
//
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new UserNotFoundException("사용자 x")
//        );
//        return user.getPosts();
//    }
//
//
//    @PostMapping("/users/{id}/posts")
//    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Integer id){
//
//        User user = retreieveUser(id).getContent();
//        post.setUser(user);
//        Post savePost = postRepository.save(post);
//
//        // header에 location 설정...
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savePost.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//}
