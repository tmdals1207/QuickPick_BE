package com.quickpick.ureca.v3.user.service;

import com.quickpick.ureca.v3.user.constants.Gender;
import com.quickpick.ureca.v3.user.domain.User;
import com.quickpick.ureca.v3.user.dto.UserCreateRequest;
import com.quickpick.ureca.v3.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(final UserCreateRequest request) {

        User user = User.builder()
                .age(request.age())
                .name(request.name())
                .gender(Gender.valueOf(request.gender()))
                .password(request.password())
                .build();

        userRepository.save(user);
    }
}
