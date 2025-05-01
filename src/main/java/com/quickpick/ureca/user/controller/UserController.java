package com.quickpick.ureca.user.controller;

import com.quickpick.ureca.user.dto.UserSignUpRequestDto;
import com.quickpick.ureca.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpRequestDto dto){
        userService.saveUser(dto);
        return ResponseEntity.ok("회원가입 완료");
    }

}
