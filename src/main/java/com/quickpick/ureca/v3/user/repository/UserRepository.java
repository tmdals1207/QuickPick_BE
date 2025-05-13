package com.quickpick.ureca.v3.user.repository;

import com.quickpick.ureca.v3.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
