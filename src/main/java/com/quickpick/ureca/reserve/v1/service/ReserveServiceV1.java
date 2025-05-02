package com.quickpick.ureca.reserve.v1.service;

import com.quickpick.ureca.ticket.v1.domain.TicketV1;
import com.quickpick.ureca.ticket.v1.repository.TicketRepositoryV1;
import com.quickpick.ureca.user.v1.domain.UserV1;
import com.quickpick.ureca.user.v1.repository.UserRepositoryV1;
import com.quickpick.ureca.userticket.v1.domain.UserTicketV1;
import com.quickpick.ureca.userticket.v1.repository.UserTicketRepositoryV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReserveServiceV1 {

    @Autowired
    private TicketRepositoryV1 ticketRepositoryV1;

    @Autowired
    private UserRepositoryV1 userRepositoryV1;

    @Autowired
    private UserTicketRepositoryV1 userTicketRepositoryV1;

//    // 티켓 예약 메서드 (락 X)
//    @Transactional
//    public void reserveTicket(Long userId, Long ticketId) {
//        // 티켓과 사용자 가져오기
//        TicketV1 ticket = ticketRepositoryV1.findById(ticketId).orElseThrow(() -> new RuntimeException("티켓을 찾을 수 없습니다."));
//        UserV1 user = userRepositoryV1.findById(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        // 티켓 재고가 없으면 예외 발생
//        if (ticket.getQuantity() <= 0) {
//            throw new RuntimeException("매진되었습니다.");
//        }
//
//        // 티켓 재고 감소
//        System.out.println("감소!");
//        ticket.decreaseCount();
//        ticketRepositoryV1.save(ticket);  // 재고 감소 후 저장
//
//        // 예약 정보 저장
//        UserTicketV1 userTicket = new UserTicketV1(user, ticket);
//        userTicketRepositoryV1.save(userTicket);
//    }

    // 티켓 예약 메서드 (비관적 락) - Pessimistic Lock
    @Transactional
    public void reserveTicket(Long userId, Long ticketId) {

        log.info(">>> reserveTicket called: userId = {}, ticketId = {}", userId, ticketId);

        UserV1 user = userRepositoryV1.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        System.out.println("Reserve Ticket");
        TicketV1 ticket = ticketRepositoryV1.findByIdForUpdate(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        if (ticket.getQuantity() <= 0) {
            throw new IllegalStateException("Ticket out of stock");
        }

        ticket.setQuantity(ticket.getQuantity() - 1);
        userTicketRepositoryV1.save(new UserTicketV1(user, ticket));
    }

}