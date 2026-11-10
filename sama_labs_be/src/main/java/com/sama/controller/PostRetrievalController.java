package com.sama.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sama.model.Post;
import com.sama.service.PostRetrievalService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRetrievalController {

    private final PostRetrievalService postService;

    public PostRetrievalController(PostRetrievalService postService) {
        this.postService = postService;
    }

    // GET /api/v1/posts/feed?page=0&size=10 (Infinite scroll feed)
    @GetMapping("/feed")
    public ResponseEntity<Page<Post>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Post> feed = postService.getFeed(page, size);
        return ResponseEntity.ok(feed);
    }

    // GET /api/v1/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // PUT /api/v1/posts/{id}/caption (Only updates title/caption)
    @PutMapping("/{id}/caption")
    public ResponseEntity<Post> updateCaption(
            @PathVariable UUID id,
            @RequestBody String newCaption) {
        Post updated = postService.updateCaption(id, newCaption);
        return ResponseEntity.ok(updated);
    }
}
