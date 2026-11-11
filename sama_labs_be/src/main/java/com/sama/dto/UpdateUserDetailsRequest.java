package com.sama.dto;

import java.util.List;

public class UpdateUserDetailsRequest {
    private List<String> userProfessions;
    private String userAmbition;
    private String currentFeeling;
    private String feelingNote;
    private String phNo;
    private String location;

    public UpdateUserDetailsRequest() {}

    public UpdateUserDetailsRequest(List<String> userProfessions, String userAmbition,
                                    String currentFeeling, String feelingNote,
                                    String phNo, String location) {
        this.userProfessions = userProfessions;
        this.userAmbition = userAmbition;
        this.currentFeeling = currentFeeling;
        this.feelingNote = feelingNote;
        this.phNo = phNo;
        this.location = location;
    }

    public static UpdateUserDetailsRequestBuilder builder() {
        return new UpdateUserDetailsRequestBuilder();
    }

    public static class UpdateUserDetailsRequestBuilder {
        private List<String> userProfessions;
        private String userAmbition;
        private String currentFeeling;
        private String feelingNote;
        private String phNo;
        private String location;

        public UpdateUserDetailsRequestBuilder userProfessions(List<String> userProfessions) {
            this.userProfessions = userProfessions;
            return this;
        }

        public UpdateUserDetailsRequestBuilder userAmbition(String userAmbition) {
            this.userAmbition = userAmbition;
            return this;
        }

        public UpdateUserDetailsRequestBuilder currentFeeling(String currentFeeling) {
            this.currentFeeling = currentFeeling;
            return this;
        }

        public UpdateUserDetailsRequestBuilder feelingNote(String feelingNote) {
            this.feelingNote = feelingNote;
            return this;
        }

        public UpdateUserDetailsRequestBuilder phNo(String phNo) {
            this.phNo = phNo;
            return this;
        }

        public UpdateUserDetailsRequestBuilder location(String location) {
            this.location = location;
            return this;
        }

        public UpdateUserDetailsRequest build() {
            return new UpdateUserDetailsRequest(userProfessions, userAmbition, currentFeeling, feelingNote, phNo, location);
        }
    }

    public List<String> getUserProfessions() { return userProfessions; }
    public void setUserProfessions(List<String> userProfessions) { this.userProfessions = userProfessions; }
    public String getUserAmbition() { return userAmbition; }
    public void setUserAmbition(String userAmbition) { this.userAmbition = userAmbition; }
    public String getCurrentFeeling() { return currentFeeling; }
    public void setCurrentFeeling(String currentFeeling) { this.currentFeeling = currentFeeling; }
    public String getFeelingNote() { return feelingNote; }
    public void setFeelingNote(String feelingNote) { this.feelingNote = feelingNote; }
    public String getPhNo() { return phNo; }
    public void setPhNo(String phNo) { this.phNo = phNo; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
