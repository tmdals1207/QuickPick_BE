package com.quickpick.ureca.user.repository;

import com.quickpick.ureca.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);             //id로 사용자 정보 가져오기
}
