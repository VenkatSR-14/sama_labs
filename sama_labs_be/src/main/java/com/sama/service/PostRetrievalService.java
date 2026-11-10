package com.sama.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sama.model.Post;
import com.sama.repository.PostRepository;

@Service
public class PostRetrievalService {

    private final PostRepository postRepository;

    public PostRetrievalService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post createPost(String title, UUID userDetailsId, String imageUrl) {
        Post post = new Post(title, userDetailsId, imageUrl);
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post getPostById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
    }

    // Paginated feed for scrollable Instagram-style viewing
    @Transactional(readOnly = true)
    public Page<Post> getFeed(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    // Cursor-based feed (loads next batch older than the last seen timestamp)
    @Transactional(readOnly = true)
    public List<Post> getFeedCursor(Instant cursor, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        if (cursor == null) {
            return postRepository.findAllByOrderByCreatedAtDesc(pageable).getContent();
        }
        return postRepository.findByCreatedAtLessThanOrderByCreatedAtDesc(cursor, pageable);
    }

    @Transactional
    public Post updateCaption(UUID postId, String newTitle) {
        Post post = getPostById(postId);
        post.setTitle(newTitle); // Only title/caption is mutable
        return postRepository.save(post);
    }
}
