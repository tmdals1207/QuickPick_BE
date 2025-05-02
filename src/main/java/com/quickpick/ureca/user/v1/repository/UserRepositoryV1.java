package com.quickpick.ureca.user.v1.repository;

import com.quickpick.ureca.user.v1.domain.UserV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryV1 extends JpaRepository<UserV1, Long> {
}
