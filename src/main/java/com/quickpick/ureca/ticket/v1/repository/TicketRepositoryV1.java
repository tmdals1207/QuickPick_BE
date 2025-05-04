package com.quickpick.ureca.ticket.v1.repository;

import com.quickpick.ureca.ticket.v1.domain.TicketV1;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepositoryV1 extends JpaRepository<TicketV1, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from TicketV1 t where t.ticketId = :ticketId")
    Optional<TicketV1> findByIdForUpdate(Long ticketId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    select t from TicketV1 t
    left join fetch t.userTickets ut
    left join fetch ut.user
    where t.ticketId = :ticketId
    """)
    Optional<TicketV1> findByIdForUpdateWithUsers(Long ticketId);

}
