package com.quickpick.ureca.auth.controller;

import com.quickpick.ureca.auth.dto.*;
import com.quickpick.ureca.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto request) {
        try {
            UserLoginResponseDto response = authService.login(request.getId(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = authService.extractToken(request);
        authService.logout(accessToken);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/auth/token")                                        //엑세스 토큰 재발급
    public ResponseEntity<?> createNewAccessToken( //ResponseEntity<CreateAccessTokenResponse>-> ResponseEntity<?>로 수정
            @RequestBody CreateAccessTokenRequest request) {
        try {
            String newAccessToken
                    = authService.createNewAccessToken(request.getRefreshToken());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CreateAccessTokenResponse(newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CreateAccessTokenErrorResponse(e.getMessage()));
        }
    }
}
