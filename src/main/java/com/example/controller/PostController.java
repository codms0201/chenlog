package com.example.controller;

import com.example.controller.dto.PostDTO;
import com.example.controller.form.CreatePostForm;
import com.example.controller.form.EditPostForm;
import com.example.entity.Post;
import com.example.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 페이지
    @GetMapping
    public String getAllPosts(Model model) {
        List<PostDTO> posts = postService.getAllPosts();  // 게시글 목록 가져오기
        model.addAttribute("posts", posts);
        return "list"; // Thymeleaf 템플릿 "list.html"
    }

    // 게시글 상세 페이지
    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        PostDTO postDTO = postService.getPostById(id);
        model.addAttribute("post", postDTO);  // 상세 게시글 모델에 추가
        return "detail"; // Thymeleaf 템플릿 "detail.html"
    }

    // 글 작성 페이지 이동
    @GetMapping("/new")
    public String createPostForm(Model model) {
        model.addAttribute("form", new CreatePostForm());  // 새 글 작성 폼
        return "new_form"; // Thymeleaf 템플릿 "createForm.html"
    }

    // 새 글 저장
    @PostMapping
    public String createPost(@ModelAttribute CreatePostForm form) {
        postService.createPost(form);
        return "redirect:/posts"; // 저장 후 목록 페이지로 이동
    }

    // 글 수정 페이지 이동
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        PostDTO postDTO = postService.getPostById(id);
        EditPostForm form = EditPostForm.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(postDTO.getAuthor())
                .build();
        model.addAttribute("form", form); // 수정할 폼을 모델에 추가
        return "edit_form"; // Thymeleaf 템플릿 "editForm.html"
    }

    // 글 수정 저장 (POST 요청을 PUT으로 변환)
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updatePost(@PathVariable Long id, @ModelAttribute EditPostForm form) {
        postService.updatePost(id, form); // 수정 처리
        return "redirect:/posts"; // 수정 후 목록 페이지로 이동
    }

    // 글 삭제
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)  // 삭제는 POST로 변환
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts"; // 삭제 후 목록 페이지로 이동
    }
}