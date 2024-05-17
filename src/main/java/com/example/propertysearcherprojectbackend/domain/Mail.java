package com.example.propertysearcherprojectbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;

    private Mail(Builder builder) {
        this.mailTo = builder.mailTo;
        this.subject = builder.subject;
        this.message = builder.message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String mailTo;
        private String subject;
        private String message;

        public Builder mailTo(String mailTo) {
            this.mailTo = mailTo;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Mail build() {
            return new Mail(this);
        }
    }
}