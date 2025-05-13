package com.quickpick.ureca.v3.user.controller;

import com.quickpick.ureca.v3.user.dto.UserCreateRequest;
import com.quickpick.ureca.v3.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v3/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody UserCreateRequest request) {
        userService.save(request);
    }
}
