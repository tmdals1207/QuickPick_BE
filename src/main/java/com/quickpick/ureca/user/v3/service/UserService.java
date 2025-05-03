package com.quickpick.ureca.user.v3.service;

import com.quickpick.ureca.user.v3.constants.Gender;
import com.quickpick.ureca.user.v3.domain.User;
import com.quickpick.ureca.user.v3.dto.UserCreateRequest;
import com.quickpick.ureca.user.v3.repository.UserRepository;
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
                .id(request.id())
                .age(request.age())
                .name(request.name())
                .gender(Gender.valueOf(request.gender()))
                .password(request.password())
                .build();

        userRepository.save(user);
    }
}
