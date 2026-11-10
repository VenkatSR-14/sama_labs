package com.sama.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sama.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findById(UUID postId);

    boolean existsById(UUID postId);

    // Paginated feed sorted by newest posts first
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Filter posts by user/author
    Page<Post> findByUserDetailsIdOrderByCreatedAtDesc(UUID userDetailsId, Pageable pageable);

    // Cursor-based pagination (Instagram style: next batch older than last seen timestamp)
    List<Post> findByCreatedAtLessThanOrderByCreatedAtDesc(Instant cursorTimestamp, Pageable pageable);
}
