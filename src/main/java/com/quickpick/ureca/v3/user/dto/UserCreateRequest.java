package com.quickpick.ureca.v3.user.dto;

public record UserCreateRequest(
        String id,
        String password,
        String name,
        int age,
        String gender
) {
}
