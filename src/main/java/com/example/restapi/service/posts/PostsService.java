/*
package com.example.restapi.service.posts;


import com.example.restapi.domain.posts.PostsRepository;
import com.example.restapi.domain.posts.Posts;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
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
    public Long save(PostsSaveRequestDto requestDto){
        User requestUser = userRepository.findByEmail(requestDto.getUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("회원이 아닙니다." + requestDto.getUserEmail()));

        return postsRepository.save(requestDto.toEntity(requestUser)).getId();
    }

    @Transactional
    public Long update(Long postId, PostsUpdateRequestDto requestDto) {
        Posts post = postsRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다 postId = " + postId));

        if(!isEqualUser(post,requestDto.getUserEmail())){
            throw new IllegalArgumentException("게시물 작성자가 아닙니다");
        }

        post.update(requestDto.getContent());

        return postId;
    }

    @Transactional
    public PostsResponseDto findById(Long postId){
        Posts entity = postsRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다 postId = " + postId));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long postId, String userEmail){
        Posts posts = postsRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId)
        );

        if(!isEqualUser(posts,userEmail)){
            throw new IllegalArgumentException("Not Matched Writer to SessionUser");
        }

        postsRepository.delete(posts);
    }

    // Post 작성자와 접속user Id가 같은지
    private boolean isEqualUser(Posts posts, String userEmail){
        return userEmail.equals(posts.getUser().getEmail());
    }
}
*/
