package com.quickpick.ureca.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {                 //로그인 응답 dto
    private String accessToken;
    private String refreshToken;
}
