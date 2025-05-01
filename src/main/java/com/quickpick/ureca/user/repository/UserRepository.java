package com.quickpick.ureca.user.repository;

import com.quickpick.ureca.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
