package com.example.controller.dto;

import com.example.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String author;

    public static PostDTO from(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .build();
    }
}