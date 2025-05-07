package com.quickpick.ureca.user.service;

import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.dto.UserSignUpRequestDto;
import com.quickpick.ureca.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void saveUser(UserSignUpRequestDto dto) {
        userRepository.save(User.builder()
                .id(dto.getId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .build());
    }
/*
    public boolean login(UserLoginRequestDto dto) {

    }
 */
    //user_id(고유 번호)로 유저 검색
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));
    }

    //id(아이디)로 유저 검색
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("User not found"));
    }
}
