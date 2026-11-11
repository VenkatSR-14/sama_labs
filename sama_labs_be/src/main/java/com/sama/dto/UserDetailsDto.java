package com.sama.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UserDetailsDto {
    private UUID userId;
    private List<String> userProfessions;
    private String userAmbition;
    private String currentFeeling;
    private String feelingNote;
    private Instant feelingUpdatedAt;
    private String phNo;
    private String location;
    private Instant createdAt;
    private Instant updatedAt;

    public UserDetailsDto() {}

    public UserDetailsDto(UUID userId, List<String> userProfessions, String userAmbition,
                          String currentFeeling, String feelingNote, Instant feelingUpdatedAt,
                          String phNo, String location, Instant createdAt, Instant updatedAt) {
        this.userId = userId;
        this.userProfessions = userProfessions;
        this.userAmbition = userAmbition;
        this.currentFeeling = currentFeeling;
        this.feelingNote = feelingNote;
        this.feelingUpdatedAt = feelingUpdatedAt;
        this.phNo = phNo;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserDetailsDtoBuilder builder() {
        return new UserDetailsDtoBuilder();
    }

    public static class UserDetailsDtoBuilder {
        private UUID userId;
        private List<String> userProfessions;
        private String userAmbition;
        private String currentFeeling;
        private String feelingNote;
        private Instant feelingUpdatedAt;
        private String phNo;
        private String location;
        private Instant createdAt;
        private Instant updatedAt;

        public UserDetailsDtoBuilder userId(UUID userId) { this.userId = userId; return this; }
        public UserDetailsDtoBuilder userProfessions(List<String> userProfessions) { this.userProfessions = userProfessions; return this; }
        public UserDetailsDtoBuilder userAmbition(String userAmbition) { this.userAmbition = userAmbition; return this; }
        public UserDetailsDtoBuilder currentFeeling(String currentFeeling) { this.currentFeeling = currentFeeling; return this; }
        public UserDetailsDtoBuilder feelingNote(String feelingNote) { this.feelingNote = feelingNote; return this; }
        public UserDetailsDtoBuilder feelingUpdatedAt(Instant feelingUpdatedAt) { this.feelingUpdatedAt = feelingUpdatedAt; return this; }
        public UserDetailsDtoBuilder phNo(String phNo) { this.phNo = phNo; return this; }
        public UserDetailsDtoBuilder location(String location) { this.location = location; return this; }
        public UserDetailsDtoBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public UserDetailsDtoBuilder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public UserDetailsDto build() {
            return new UserDetailsDto(userId, userProfessions, userAmbition, currentFeeling,
                    feelingNote, feelingUpdatedAt, phNo, location, createdAt, updatedAt);
        }
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public List<String> getUserProfessions() { return userProfessions; }
    public void setUserProfessions(List<String> userProfessions) { this.userProfessions = userProfessions; }
    public String getUserAmbition() { return userAmbition; }
    public void setUserAmbition(String userAmbition) { this.userAmbition = userAmbition; }
    public String getCurrentFeeling() { return currentFeeling; }
    public void setCurrentFeeling(String currentFeeling) { this.currentFeeling = currentFeeling; }
    public String getFeelingNote() { return feelingNote; }
    public void setFeelingNote(String feelingNote) { this.feelingNote = feelingNote; }
    public Instant getFeelingUpdatedAt() { return feelingUpdatedAt; }
    public void setFeelingUpdatedAt(Instant feelingUpdatedAt) { this.feelingUpdatedAt = feelingUpdatedAt; }
    public String getPhNo() { return phNo; }
    public void setPhNo(String phNo) { this.phNo = phNo; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
