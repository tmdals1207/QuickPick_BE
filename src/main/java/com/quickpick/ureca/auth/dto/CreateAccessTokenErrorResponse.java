package com.quickpick.ureca.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenErrorResponse {
    private String error;

    public CreateAccessTokenErrorResponse(String error) {
        this.error = error;
    }
}
