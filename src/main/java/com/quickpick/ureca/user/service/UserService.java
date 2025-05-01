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
}
