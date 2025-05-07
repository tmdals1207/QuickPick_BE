package com.quickpick.ureca.auth.service;

import com.quickpick.ureca.auth.config.TokenProvider;
import com.quickpick.ureca.auth.dto.UserLoginResponseDto;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public UserLoginResponseDto login(String id, String password) {                             //jwt 로그인
        User user = userService.findById(id);
        
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {       //비밀번호 일치 검증
            throw new BadCredentialsException("Invalid password");
        }

        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(14));           //로그인 성공 시 토큰 발급

        refreshTokenService.save(user.getUserId(), refreshToken);

        return new UserLoginResponseDto(accessToken, refreshToken);
    }

    public String createNewAccessToken(String refreshToken) {                                   //리프레시 토큰을 이용한 엑세스 토큰 재발급
        //리프레시 토큰이 유효하지 않으면 에러
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findByUserId(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
