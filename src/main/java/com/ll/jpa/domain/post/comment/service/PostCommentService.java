package com.ll.jpa.domain.post.comment.service;

import com.ll.jpa.domain.post.comment.entity.PostComment;
import com.ll.jpa.domain.post.comment.repository.PostCommentRepository;
import com.ll.jpa.domain.post.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;

    public PostComment write(Post post, String content) {
        PostComment postComment = PostComment.builder()
                .post(post)
                .content(content)
                .build();

        return postCommentRepository.save(postComment);
    }

    public Optional<PostComment> findById(long id) {
        return postCommentRepository.findById(id);
    }

    public void delete(PostComment postComment) {
        postCommentRepository.delete(postComment);
    }
}
