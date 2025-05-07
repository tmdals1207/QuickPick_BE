package com.quickpick.ureca.auth.service;

import com.quickpick.ureca.auth.config.TokenProvider;
import com.quickpick.ureca.auth.dto.UserLoginResponseDto;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final RedisTemplate<String, String> redisTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //jwt 로그인
    public UserLoginResponseDto login(String id, String password) {
        User user = userService.findById(id);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {       //비밀번호 일치 검증
            throw new BadCredentialsException("Invalid password");
        }

        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(14));           //로그인 성공 시 토큰 발급

        refreshTokenService.save(user.getUserId(), refreshToken);

        return new UserLoginResponseDto(accessToken, refreshToken);
    }

    // 토큰 추출 (Authorization 헤더에서 Bearer 제거)
    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");                 //Authorization값을 가지는 헤더 가져오기
        if (authHeader != null && authHeader.startsWith("Bearer ")) {          
            return authHeader.substring(7);                             //앞에 Bearer 를 제거해 토큰 값만 가져오기
        }
        throw new RuntimeException("Missing or invalid Authorization header");
    }

    //로그아웃
    public void logout(String token) {
        if (!tokenProvider.validToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        long expiration = tokenProvider.getRemainingValidity(token);                //토큰의 남은 유효시간 계산
        redisTemplate.opsForValue().set("blacklist:" + token, "logout", expiration, TimeUnit.MILLISECONDS); //남은 유효시간 만큼 블랙리스트에 넣기
    }

    //리프레시 토큰을 이용한 엑세스 토큰 재발급
    public String createNewAccessToken(String refreshToken) {
        //리프레시 토큰이 유효하지 않으면 에러
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findByUserId(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
