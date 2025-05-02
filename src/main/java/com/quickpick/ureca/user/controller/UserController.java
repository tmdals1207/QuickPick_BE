package com.quickpick.ureca.user.controller;

import com.quickpick.ureca.user.dto.UserLoginRequestDto;
import com.quickpick.ureca.user.dto.UserSignUpRequestDto;
import com.quickpick.ureca.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpRequestDto dto) {
        userService.saveUser(dto);
        return ResponseEntity.ok("회원가입 완료");
    }
/*
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto dto){
        boolean isSuccess = userService.login(dto);

        if (isSuccess) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호가 틀렸습니다.");
        }
    }
*/
/*
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = userService.findByUsername(request.getUsername());
            String token = tokenProvider.generateToken(user, Duration.ofHours(2));

            return ResponseEntity.ok(new TokenResponse(token));

        } catch (AuthenticationException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
    */
}
