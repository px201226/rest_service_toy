package com.example.restapi.service.comments;

import com.example.restapi.domain.comments.Comments;
import com.example.restapi.domain.comments.CommentsRepository;
import com.example.restapi.domain.posts.Posts;
import com.example.restapi.domain.posts.PostsRepository;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.CommentNotFoundException;
import com.example.restapi.exception.exceptions.EmailSigninFailedException;
import com.example.restapi.exception.exceptions.PostsNotFoundException;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import com.example.restapi.exception.high.NotExistDataException;
import com.example.restapi.web.dto.CommentsSaveRequestDto;
import com.example.restapi.web.dto.CommentsUpdateRequestDto;
import com.example.restapi.web.dto.PostsSaveRequestDto;
import com.example.restapi.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public Comments save(CommentsSaveRequestDto requestDto, Long postId, String userEmail){

        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Posts posts = postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new);

        return commentsRepository.save(requestDto.toEntity(user, posts));
    }

    @Transactional
    public Comments update(CommentsUpdateRequestDto requestDto, Long postId, Long commentId, String userEmail) {

        Posts posts = postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        Comments comments = commentsRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(!isEqualUser(comments,userEmail)){
            throw new EmailSigninFailedException();
        }
        comments.update(requestDto.getContent());

        return comments;
    }

    @Transactional
    public Comments findById(Long postId, Long commentId){
        Posts posts = postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        Comments entity = commentsRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        return entity;
    }

    @Transactional(readOnly = true)
    public List<Comments> findAllDesc(){
        return commentsRepository.findAllDesc();
    }

    @Transactional
    public void delete(Long postId, Long commentId, String userEmail){

        Posts posts = postsRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        Comments comments = commentsRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(!isEqualUser(comments,userEmail)){
            throw new EmailSigninFailedException();
        }

        commentsRepository.delete(comments);
    }

    // Post 작성자와 접속user Id가 같은지
    private boolean isEqualUser(Comments comments, String userEmail){
        return userEmail.equals(comments.getUser().getEmail());
    }
}
