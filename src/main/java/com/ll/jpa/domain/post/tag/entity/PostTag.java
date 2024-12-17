package com.ll.jpa.domain.post.tag.entity;

import com.ll.jpa.domain.post.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(PostTagId.class)
public class PostTag {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    private Post post;

    @Id
    @Column(length = 30)
    @EqualsAndHashCode.Include
    private String content;
}