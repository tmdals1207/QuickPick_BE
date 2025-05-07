package com.quickpick.ureca.user.controller;

import com.quickpick.ureca.user.dto.UserSignUpRequestDto;
import com.quickpick.ureca.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpRequestDto dto) {
        userService.saveUser(dto);
        return ResponseEntity.ok("회원가입 완료");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("테스트 성공");
    }


}
