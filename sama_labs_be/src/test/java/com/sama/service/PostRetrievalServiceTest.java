package com.sama.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sama.model.Post;
import com.sama.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class PostRetrievalServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostRetrievalService postService;

    private UUID samplePostId;
    private UUID sampleUserId;
    private Post samplePost;

    @BeforeEach
    void setUp() {
        samplePostId = UUID.randomUUID();
        sampleUserId = UUID.randomUUID();
        samplePost = new Post("A quiet mind brings eternal peace", sampleUserId, "https://supabase.co/storage/post1.webp");
    }

    @Test
    void testCreatePost() {
        when(postRepository.save(any(Post.class))).thenReturn(samplePost);

        Post created = postService.createPost("A quiet mind brings eternal peace", sampleUserId, "https://supabase.co/storage/post1.webp");

        assertNotNull(created);
        assertEquals("A quiet mind brings eternal peace", created.getTitle());
        assertEquals("https://supabase.co/storage/post1.webp", created.getImageUrl());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void testGetPostById_Found() {
        when(postRepository.findById(samplePostId)).thenReturn(Optional.of(samplePost));

        Post post = postService.getPostById(samplePostId);

        assertNotNull(post);
        assertEquals("A quiet mind brings eternal peace", post.getTitle());
        verify(postRepository).findById(samplePostId);
    }

    @Test
    void testGetPostById_NotFound() {
        when(postRepository.findById(samplePostId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> postService.getPostById(samplePostId));
    }

    @Test
    void testGetFeed() {
        Page<Post> page = new PageImpl<>(List.of(samplePost));
        when(postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10))).thenReturn(page);

        Page<Post> result = postService.getFeed(0, 10);

        assertEquals(1, result.getContent().size());
        verify(postRepository).findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10));
    }

    @Test
    void testGetFeedCursor() {
        Instant now = Instant.now();
        when(postRepository.findByCreatedAtLessThanOrderByCreatedAtDesc(any(Instant.class), any())).thenReturn(List.of(samplePost));

        List<Post> posts = postService.getFeedCursor(now, 10);

        assertEquals(1, posts.size());
        verify(postRepository).findByCreatedAtLessThanOrderByCreatedAtDesc(any(Instant.class), any());
    }

    @Test
    void testUpdateCaption() {
        when(postRepository.findById(samplePostId)).thenReturn(Optional.of(samplePost));
        when(postRepository.save(samplePost)).thenReturn(samplePost);

        Post updated = postService.updateCaption(samplePostId, "New calm caption");

        assertEquals("New calm caption", updated.getTitle());
        verify(postRepository).save(samplePost);
    }
}

