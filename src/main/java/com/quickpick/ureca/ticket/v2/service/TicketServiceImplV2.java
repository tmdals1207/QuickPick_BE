package com.quickpick.ureca.ticket.v2.service;

import com.quickpick.ureca.ticket.repository.TicketRepository;
import com.quickpick.ureca.ticket.v2.domain.Ticket;
import com.quickpick.ureca.ticket.v2.repository.TicketRepositoryV2;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.repository.UserRepository;
import com.quickpick.ureca.userticket.v2.domain.UserTicket;
import com.quickpick.ureca.userticket.v2.repository.UserTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImplV2 implements TicketServiceV2 {
    private final TicketRepositoryV2 ticketRepository;
    private final UserRepository userRepository;
    private final UserTicketRepository userTicketRepository;


    @Override
    @Transactional
    public void orderTicket(Long ticketId, Long userId) {
        int retry = 3;
        while (retry-- > 0) {
            try {
                processOrder(ticketId, userId);
                return; // 성공하면 종료
            } catch (ObjectOptimisticLockingFailureException e) {
                if (retry == 0) throw e;
            }
        }
    }

    private void processOrder(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 티켓입니다."));

        if (ticket.getQuantity() <= 0) {
            throw new RuntimeException("매진된 티켓입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        if (userTicketRepository.existsByUserAndTicket(user, ticket)) {
            throw new RuntimeException("이미 예매한 티켓입니다.");
        }

        ticket.setQuantity(ticket.getQuantity() - 1);
        ticketRepository.save(ticket); // save() 시 OptimisticLock 체크 발생

        UserTicket userTicket = new UserTicket(user, ticket);
        userTicketRepository.save(userTicket);
    }


    @Override
    public void cancelTicket(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 티켓입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        UserTicket userTicket = userTicketRepository.findByUserAndTicket(user, ticket)
                .orElseThrow(() -> new RuntimeException("예매 기록이 없습니다."));

        userTicketRepository.delete(userTicket);

        ticket.setQuantity(ticket.getQuantity() + 1);
        ticketRepository.save(ticket);
    }
}
