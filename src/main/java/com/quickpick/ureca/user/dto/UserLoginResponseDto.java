package com.quickpick.ureca.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDto {                 //로그인 응답 dto
    private Long userId;
    private String name;
    //private String token;
}
