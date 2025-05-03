package com.quickpick.ureca.user.v3.repository;

import com.quickpick.ureca.user.v3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
