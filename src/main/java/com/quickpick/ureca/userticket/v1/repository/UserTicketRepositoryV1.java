package com.quickpick.ureca.userticket.v1.repository;

import com.quickpick.ureca.userticket.v1.domain.UserTicketV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTicketRepositoryV1 extends JpaRepository<UserTicketV1, Long> {
}
