package com.quickpick.ureca.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickpick.ureca.auth.dto.UserLoginResponseDto;
import com.quickpick.ureca.auth.service.RefreshTokenService;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {    //OAuth 인증 성공시 jwt  발급 및 리디렉션
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 사용자 DB에 저장 (없으면 새로 추가)
        User user = userService.findById(email)
                .orElseGet(() -> userService.saveFromOAuth2(oAuth2User));

        // JWT 발급
        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));  // Access token
        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(14)); // Refresh token (필요시 DB 저장)

        // Refresh token을 DB에 저장
        refreshTokenService.save(user.getUserId(), refreshToken);

        // 기존 로그인 응답 DTO 사용
        UserLoginResponseDto responseDto = new UserLoginResponseDto(accessToken, refreshToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getWriter(), responseDto);
    }
}
