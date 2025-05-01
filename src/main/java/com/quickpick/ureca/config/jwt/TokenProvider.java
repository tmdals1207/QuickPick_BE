package com.quickpick.ureca.config.jwt;

import com.quickpick.ureca.user.domain.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;


    public String generateToken(User user, Date expiredAt) {
        Date now = new Date();
        return Jwts.builder()
                //.setHeaderParam(Header.TYPE, Header.JWT_TYPE) //deprecated, 이제 안 써도 라이브러리가 자동적으로 처리?
                .issuer(jwtProperties.getIssuer())
                .expiration(expiredAt)
                .subject(user.getId())
                .claim("user_id", user.getUserId())
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
