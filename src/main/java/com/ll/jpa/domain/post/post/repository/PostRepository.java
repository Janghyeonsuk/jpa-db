package com.ll.jpa.domain.post.post.repository;

import com.ll.jpa.domain.post.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);

    List<Post> findByTitleAndContent(String title, String content);

    List<Post> findByTitleLike(String title);

    List<Post> findByTitleLikeOrderByIdDesc(String title);

    List<Post> findByOrderByIdDesc();

    List<Post> findTop2ByTitleLikeOrderByIdDesc(String title);

    List<Post> findTop2ByOrderByIdDesc();

    Page<Post> findByTitleLike(String title, Pageable pageable);

    List<Post> findByAuthorNickname(String authorNickname);

    List<Post> findByCommentsAuthorNickname(String authorNickname);
}