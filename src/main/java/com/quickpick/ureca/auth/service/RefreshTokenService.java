package com.quickpick.ureca.auth.service;

import com.quickpick.ureca.auth.domain.RefreshToken;
import com.quickpick.ureca.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
    }

    //refresh 토큰 저장 (일단 db 저장, redis 저장도 고려 중)
    public void save(Long userId, String refreshToken) {
        RefreshToken token = new RefreshToken(userId, refreshToken);
        refreshTokenRepository.save(token);
    }
}
