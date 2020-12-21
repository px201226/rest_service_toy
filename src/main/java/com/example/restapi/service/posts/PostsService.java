package com.example.restapi.service.posts;


import com.example.restapi.domain.posts.PostsRepository;
import com.example.restapi.domain.posts.Posts;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.EmailSigninFailedException;
import com.example.restapi.exception.exceptions.PostsNotFoundException;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import com.example.restapi.exception.high.NotExistDataException;
import com.example.restapi.web.dto.PostsListResponseDto;
import com.example.restapi.web.dto.PostsResponseDto;
import com.example.restapi.web.dto.PostsSaveRequestDto;
import com.example.restapi.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    public Posts save(PostsSaveRequestDto requestDto, String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        return postsRepository.save(requestDto.toEntity(user));
    }

    @Transactional
    public Posts update(Long postId, PostsUpdateRequestDto requestDto, String userEmail) {
        Posts post = postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new);

        if(!isEqualUser(post,userEmail)){
            throw new EmailSigninFailedException();
        }

        post.update(requestDto.getContent());

        return post;
    }

    @Transactional
    public Posts findById(Long postId){
        Posts entity = postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new);

        return entity;
    }

    @Transactional(readOnly = true)
    public List<Posts> findAllDesc(){
        return postsRepository.findAllDesc();
    }

    @Transactional
    public void delete(Long postId, String userEmail){
        Posts posts = postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new
        );

        if(!isEqualUser(posts,userEmail)){
            throw new EmailSigninFailedException();
        }

        postsRepository.delete(posts);
    }

    // Post 작성자와 접속user Id가 같은지
    private boolean isEqualUser(Posts posts, String userEmail){
        return userEmail.equals(posts.getUser().getEmail());
    }
}
