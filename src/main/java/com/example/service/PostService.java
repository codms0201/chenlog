package com.example.service;

import com.example.controller.dto.PostDTO;
import com.example.controller.form.CreatePostForm;
import com.example.controller.form.EditPostForm;
import com.example.entity.Post;
import com.example.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 모든 게시글 조회
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostDTO::from)  // PostDTO의 from 메소드 사용
                .collect(Collectors.toList());
    }

    // 게시글 ID로 조회
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }
        return PostDTO.from(post);  // PostDTO의 from 메소드 사용
    }

    // 새 게시글 생성
    public PostDTO createPost(CreatePostForm form) {
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setAuthor(form.getAuthor());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.add(post);  // id는 자동 생성되므로 설정하지 않아도 됨
        return PostDTO.from(post);
    }

    // 게시글 수정
    public PostDTO updatePost(Long id, EditPostForm form) {
        Post post = postRepository.findById(id);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setAuthor(form.getAuthor());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.update(post);
        return PostDTO.from(post);  // PostDTO의 from 메소드 사용
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        postRepository.delete(id);
    }
}