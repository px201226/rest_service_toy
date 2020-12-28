package com.example.restapi.service.posts;


import com.example.restapi.domain.posts.PostRepository;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.EmailSigninFailedException;
import com.example.restapi.exception.exceptions.PostsNotFoundException;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import com.example.restapi.web.dto.PostsSaveRequestDto;
import com.example.restapi.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post save(PostsSaveRequestDto requestDto, String userEmail){
        return userRepository.findByEmail(userEmail)
                .map( u -> postRepository.save(requestDto.toEntity(u)))
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public Post update(Long postId, PostsUpdateRequestDto requestDto, String userEmail) {

        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);

        if(!post.isEqualUserEmail(userEmail)){
            throw new EmailSigninFailedException();
        }

        post.update(requestDto.getContent());

        return post;
    }

    @Transactional
    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllDesc(){
        return postRepository.findAllDesc();
    }

    @Transactional
    public void delete(Long postId, String userEmail){
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new
        );

        if(!post.isEqualUserEmail(userEmail)){
            throw new EmailSigninFailedException();
        }

        postRepository.delete(post);
    }

}
