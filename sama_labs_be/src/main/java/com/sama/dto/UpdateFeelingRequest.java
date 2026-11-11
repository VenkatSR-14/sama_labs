package com.sama.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateFeelingRequest {

    @NotBlank(message = "Current feeling is required")
    private String currentFeeling;

    private String feelingNote;

    public UpdateFeelingRequest() {}

    public UpdateFeelingRequest(String currentFeeling, String feelingNote) {
        this.currentFeeling = currentFeeling;
        this.feelingNote = feelingNote;
    }

    public static UpdateFeelingRequestBuilder builder() {
        return new UpdateFeelingRequestBuilder();
    }

    public static class UpdateFeelingRequestBuilder {
        private String currentFeeling;
        private String feelingNote;

        public UpdateFeelingRequestBuilder currentFeeling(String currentFeeling) {
            this.currentFeeling = currentFeeling;
            return this;
        }

        public UpdateFeelingRequestBuilder feelingNote(String feelingNote) {
            this.feelingNote = feelingNote;
            return this;
        }

        public UpdateFeelingRequest build() {
            return new UpdateFeelingRequest(currentFeeling, feelingNote);
        }
    }

    public String getCurrentFeeling() {
        return currentFeeling;
    }

    public void setCurrentFeeling(String currentFeeling) {
        this.currentFeeling = currentFeeling;
    }

    public String getFeelingNote() {
        return feelingNote;
    }

    public void setFeelingNote(String feelingNote) {
        this.feelingNote = feelingNote;
    }
}
