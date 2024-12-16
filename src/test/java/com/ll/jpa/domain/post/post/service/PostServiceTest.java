package com.ll.jpa.domain.post.post.service;

import com.ll.jpa.domain.member.member.entity.Member;
import com.ll.jpa.domain.member.member.service.MemberService;
import com.ll.jpa.domain.post.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("글 2개 생성")
    @Transactional
    void t1() {
        Member user1 = memberService.findByUsername("user1").get();

        postService.write(user1, "title1", "content1");
        postService.write(user1, "title2", "content2");
    }

    @Test
    @DisplayName("findAll")
    void t2() {
        List<Post> all = postService.findAll();
        assertEquals(3, all.size());

        Post post = all.get(0);
        assertEquals("title1", post.getTitle());
    }

    @Test
    @DisplayName("findById")
    void t3() {
        Optional<Post> opPost = postService.findById(1);

        if (opPost.isPresent()) {
            Post post = opPost.get();
            assertEquals("title1", post.getTitle());
        }
    }

    @Test
    @DisplayName("findByTitle")
    void t4() {
        Post post = postService.findByTitle("title1").get(0);
        assertEquals(1, post.getId());
    }

    @Test
    @DisplayName("findByTitleAndContent")
        // SELECT * FROM post WHERE title = 'title1' AND content = 'content1';
    void t5() {
        Post post = postService.findByTitleAndContent("title1", "content1").get(0);
        assertEquals(1, post.getId());
    }

    @Test
    @DisplayName("findByTitleLike")
        // SELECT * FROM post WHERE title LIKE '%2';
    void t6() {
        List<Post> posts = postService.findByTitleLike("%2");
        Post post = posts.get(0);
        assertEquals("title2", post.getTitle());
    }


    @Test
    @DisplayName("findByTitleLikeOrderByIdDesc")
        // SELECT * FROM post WHERE title LIKE 'title%' ORDER BY id DESC;
    void t7() {
        List<Post> posts = postService.findByTitleLikeOrderByIdDesc("title%");
        assertEquals(3, posts.size());

        Post post = posts.get(0);
        assertEquals(3, post.getId());
        assertEquals("title3", post.getTitle());
    }

    @Test
    @DisplayName("findByOrderByIdDesc")
        // SELECT * FROM post ORDER BY id DESC;
    void t8() {
        List<Post> posts = postService.findByOrderByIdDesc();
        assertEquals(3, posts.size());

        Post post = posts.get(0);
        assertEquals(3, post.getId());
        assertEquals("title3", post.getTitle());
    }

    @Test
    @DisplayName("findTop2ByTitleLikeOrderByIdDesc")
        // SELECT * FROM post WHERE title LIKE 'title%' ORDER BY id DESC LIMIT 2;
    void t9() {
        List<Post> posts = postService.findTop2ByTitleLikeOrderByIdDesc("title%");
        assertEquals(2, posts.size());

        Post post = posts.get(0);
        assertEquals(3, post.getId());
        assertEquals("title3", post.getTitle());
    }

    @Test
    @DisplayName("findTop2ByOrderByIdDesc")
        // SELECT * FROM post WHERE ORDER BY id DESC LIMIT 2;
    void t10() {
        List<Post> posts = postService.findTop2ByOrderByIdDesc("title%");
        assertEquals(2, posts.size());

        Post post = posts.get(0);
        assertEquals(3, post.getId());
        assertEquals("title3", post.getTitle());
    }

    @Test
    @DisplayName("findAll(Pageable pageable)")
        // SELECT * FROM post ORDER BY id DESC LIMIT 2, 2;
    void t11() {
        int itemsPerPage = 2; // 한 페이지에 보여줄 아이템 수
        int pageNumber = 2; // 현재 페이지 == 2
        pageNumber--; // 1을 빼는 이유는 jpa는 페이지 번호를 0부터 시작하기 때문
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> postPage = postService.findAll(pageable);
        List<Post> posts = postPage.getContent();
        assertEquals(1, posts.size()); // 글이 총 3개이고, 현재 페이지는 2이므로 1개만 보여야 함

        Post post = posts.get(0);
        assertEquals(1, post.getId());
        assertEquals("title1", post.getTitle());

        assertEquals(3, postPage.getTotalElements()); // 전체 글 수
        assertEquals(2, postPage.getTotalPages()); // 전체 페이지 수
        assertEquals(1, postPage.getNumberOfElements()); // 현재 페이지에 노출된 글 수
        assertEquals(pageNumber, postPage.getNumber()); // 현재 페이지 번호
    }

    @Test
    @DisplayName("findByTitleLike(Pageable pageable)")
        // SELECT * FROM post WHERE title LIKE 'title%' ORDER BY id DESC LIMIT 2, 2;
    void t12() {
        int itemsPerPage = 2; // 한 페이지에 보여줄 아이템 수
        int pageNumber = 2; // 현재 페이지 == 2
        pageNumber--; // 1을 빼는 이유는 jpa는 페이지 번호를 0부터 시작하기 때문
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> postPage = postService.findByTitleLike("title%", pageable);
        List<Post> posts = postPage.getContent();
        assertEquals(1, posts.size()); // 글이 총 3개이고, 현재 페이지는 2이므로 1개만 보여야 함

        Post post = posts.get(0);
        assertEquals(1, post.getId());
        assertEquals("title1", post.getTitle());

        assertEquals(3, postPage.getTotalElements()); // 전체 글 수
        assertEquals(2, postPage.getTotalPages()); // 전체 페이지 수
        assertEquals(1, postPage.getNumberOfElements()); // 현재 페이지에 노출된 글 수
        assertEquals(pageNumber, postPage.getNumber()); // 현재 페이지 번호
    }

    @Test
    @DisplayName("findByTitleLike(Pageable pageable)")
        // SELECT * FROM post WHERE title LIKE 'title%' ORDER BY id DESC LIMIT 0, 10;
    void t13() {
        int itemsPerPage = 10; // 한 페이지에 보여줄 아이템 수
        int pageNumber = 1; // 현재 페이지 == 2
        pageNumber--; // 1을 빼는 이유는 jpa는 페이지 번호를 0부터 시작하기 때문
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> postPage = postService.findByTitleLike("title%", pageable);
        List<Post> posts = postPage.getContent();
        assertEquals(3, posts.size()); // 글이 총 3개이고, 현재 페이지는 2이므로 1개만 보여야 함

        Post post = posts.get(0);
        assertEquals(3, post.getId());
        assertEquals("title3", post.getTitle());

        assertEquals(3, postPage.getTotalElements()); // 전체 글 수
        assertEquals(1, postPage.getTotalPages()); // 전체 페이지 수
        assertEquals(3, postPage.getNumberOfElements()); // 현재 페이지에 노출된 글 수
        assertEquals(pageNumber, postPage.getNumber()); // 현재 페이지 번호
    }

    @Test
    @DisplayName("findByAuthorNickname")
    void t14() {
        List<Post> posts = postService.findByAuthorNickname("유저1");

        assertEquals(2, posts.size());
    }

    @Test
    @DisplayName("findByCommentsAuthorNickname")
    void t15() {
        List<Post> posts = postService.findByCommentsAuthorNickname("유저3");

        assertEquals(1, posts.size());
    }
}