package com.gopala.job_portal.error;

import lombok.Data;

@Data
public class ErrorMessage {
        private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
