package com.sama.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "motivational_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // MUTABLE: Users can edit the title or caption
    @Column(nullable = false, length = 500)
    private String title;

    // IMMUTABLE: Author ID cannot change after creation
    @Column(name = "user_details_id", nullable = false, updatable = false)
    private UUID userDetailsId;

    // URL pointing to the image in Supabase Storage or CDN (Immutable)
    @Column(name = "image_url", nullable = false, length = 1000, updatable = false)
    private String imageUrl;

    // IMMUTABLE: Creation timestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // AUTOMATIC: Updated on every edit
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    // 1. No-Args Constructor (Required by JPA)
    public Post() {}

    // 2. All-Args Constructor
    public Post(UUID id, String title, UUID userDetailsId, String imageUrl, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.userDetailsId = userDetailsId;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // 3. Convenience Constructor for creating a new post
    public Post(String title, UUID userDetailsId, String imageUrl) {
        this.title = title;
        this.userDetailsId = userDetailsId;
        this.imageUrl = imageUrl;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    // Only mutable field has a setter:
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getUserDetailsId() {
        return userDetailsId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
