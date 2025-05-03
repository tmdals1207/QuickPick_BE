package com.quickpick.ureca.user.v3.dto;

public record UserCreateRequest(
        String id,
        String password,
        String name,
        int age,
        String gender
) {
}
