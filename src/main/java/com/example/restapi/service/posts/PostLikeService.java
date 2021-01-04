package com.example.restapi.service.posts;


import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.posts.PostRepository;
import com.example.restapi.domain.posts.like.PostLike;
import com.example.restapi.domain.posts.like.PostLikeRepository;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.EmailSigninFailedException;
import com.example.restapi.exception.exceptions.PostsNotFoundException;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import com.example.restapi.web.dto.PostsSaveRequestDto;
import com.example.restapi.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public PostLike like(Long postId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        return postLikeRepository.save(
                PostLike.builder()
                .post(post)
                .user(user)
                .build());
    }

    @Transactional
    public void unLike(Long postId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        postLikeRepository.delete(PostLike.builder()
                .post(post)
                .user(user)
                .build());
    }

    @Transactional
    public Optional<PostLike> findByUserEmailAndPostId(String userEmail, Long postId){

        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        return postLikeRepository.findByUserIdAndPostId(postId,user.getId());
    }
}
