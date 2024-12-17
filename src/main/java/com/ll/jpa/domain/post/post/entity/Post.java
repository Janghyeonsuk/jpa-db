package com.ll.jpa.domain.post.post.entity;

import com.ll.jpa.domain.member.member.entity.Member;
import com.ll.jpa.domain.post.comment.entity.PostComment;
import com.ll.jpa.domain.post.tag.entity.PostTag;
import com.ll.jpa.global.jpa.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post extends BaseTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean blind;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<PostTag> tags = new HashSet<>();

    public void addComment(Member author, String content) {
        PostComment postComment = PostComment
                .builder()
                .post(this)
                .author(author)
                .content(content)
                .build();

        comments.add(postComment);
    }

    public void addTag(String content) {
        PostTag postTag = PostTag
                .builder()
                .post(this)
                .content(content)
                .build();

        tags.add(postTag);
    }

    public boolean removeComment(PostComment comment) {
        return comments.remove(comment);
    }
}