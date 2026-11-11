package com.sama.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails {

    // 1-to-1 matching with app_users.id
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    // What they work as (e.g. ["Software Engineer", "Writer"])
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_professions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "profession")
    private List<String> userProfessions = new ArrayList<>();

    // Ambition or long-term spiritual / life goal
    @Column(name = "user_ambition", length = 500)
    private String userAmbition;

    // CORE RECOMMENDATION DRIVER: How they are feeling right now
    // Examples: "Seeking Peace", "Restless", "Grateful", "Anxious", "Deep Focus"
    @Column(name = "current_feeling", length = 100)
    private String currentFeeling;

    // Optional context: e.g. "Work deadline pressure" or "Morning meditation"
    @Column(name = "feeling_note", length = 500)
    private String feelingNote;

    // Timestamp of when the user logged this feeling (crucial for recommendation freshness)
    @Column(name = "feeling_updated_at")
    private Instant feelingUpdatedAt = Instant.now();

    @Column(name = "phone_number", unique = true, length = 30)
    private String phNo;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    // 1. No-Args Constructor (Required by JPA)
    public UserDetails() {
    }

    // 2. All-Args Constructor
    public UserDetails(UUID userId, List<String> userProfessions, String userAmbition,
                       String currentFeeling, String feelingNote, Instant feelingUpdatedAt,
                       String phNo, String location, Instant createdAt, Instant updatedAt) {
        this.userId = userId;
        this.userProfessions = (userProfessions != null) ? userProfessions : new ArrayList<>();
        this.userAmbition = userAmbition;
        this.currentFeeling = currentFeeling;
        this.feelingNote = feelingNote;
        this.feelingUpdatedAt = (feelingUpdatedAt != null) ? feelingUpdatedAt : Instant.now();
        this.phNo = phNo;
        this.location = location;
        this.createdAt = (createdAt != null) ? createdAt : Instant.now();
        this.updatedAt = (updatedAt != null) ? updatedAt : Instant.now();
    }

    // 3. Convenience Constructor
    public UserDetails(UUID userId, String userAmbition, String currentFeeling) {
        this.userId = userId;
        this.userAmbition = userAmbition;
        this.currentFeeling = currentFeeling;
        this.userProfessions = new ArrayList<>();
        this.feelingUpdatedAt = Instant.now();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) this.createdAt = Instant.now();
        if (this.updatedAt == null) this.updatedAt = Instant.now();
        if (this.feelingUpdatedAt == null) this.feelingUpdatedAt = Instant.now();
        if (this.userProfessions == null) this.userProfessions = new ArrayList<>();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Domain helper to update mood anytime throughout the day
    public void updateFeeling(String newFeeling, String note) {
        this.currentFeeling = newFeeling;
        this.feelingNote = note;
        this.feelingUpdatedAt = Instant.now();
    }

    // Builder pattern
    public static UserDetailsBuilder builder() {
        return new UserDetailsBuilder();
    }

    public static class UserDetailsBuilder {
        private UUID userId;
        private List<String> userProfessions = new ArrayList<>();
        private String userAmbition;
        private String currentFeeling;
        private String feelingNote;
        private Instant feelingUpdatedAt = Instant.now();
        private String phNo;
        private String location;
        private Instant createdAt = Instant.now();
        private Instant updatedAt = Instant.now();

        public UserDetailsBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public UserDetailsBuilder userProfessions(List<String> userProfessions) {
            this.userProfessions = userProfessions;
            return this;
        }

        public UserDetailsBuilder userAmbition(String userAmbition) {
            this.userAmbition = userAmbition;
            return this;
        }

        public UserDetailsBuilder currentFeeling(String currentFeeling) {
            this.currentFeeling = currentFeeling;
            return this;
        }

        public UserDetailsBuilder feelingNote(String feelingNote) {
            this.feelingNote = feelingNote;
            return this;
        }

        public UserDetailsBuilder feelingUpdatedAt(Instant feelingUpdatedAt) {
            this.feelingUpdatedAt = feelingUpdatedAt;
            return this;
        }

        public UserDetailsBuilder phNo(String phNo) {
            this.phNo = phNo;
            return this;
        }

        public UserDetailsBuilder location(String location) {
            this.location = location;
            return this;
        }

        public UserDetailsBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserDetailsBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(userId, userProfessions, userAmbition, currentFeeling, feelingNote,
                    feelingUpdatedAt, phNo, location, createdAt, updatedAt);
        }
    }

    // --- Getters and Setters ---

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<String> getUserProfessions() {
        return userProfessions;
    }

    public void setUserProfessions(List<String> userProfessions) {
        this.userProfessions = userProfessions;
    }

    public String getUserAmbition() {
        return userAmbition;
    }

    public void setUserAmbition(String userAmbition) {
        this.userAmbition = userAmbition;
    }

    public String getCurrentFeeling() {
        return currentFeeling;
    }

    public void setCurrentFeeling(String currentFeeling) {
        this.currentFeeling = currentFeeling;
        this.feelingUpdatedAt = Instant.now();
    }

    public String getFeelingNote() {
        return feelingNote;
    }

    public void setFeelingNote(String feelingNote) {
        this.feelingNote = feelingNote;
    }

    public Instant getFeelingUpdatedAt() {
        return feelingUpdatedAt;
    }

    public void setFeelingUpdatedAt(Instant feelingUpdatedAt) {
        this.feelingUpdatedAt = feelingUpdatedAt;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
