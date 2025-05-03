package com.quickpick.ureca.userticket.v3.repository;

import com.quickpick.ureca.userticket.v3.domain.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

}
