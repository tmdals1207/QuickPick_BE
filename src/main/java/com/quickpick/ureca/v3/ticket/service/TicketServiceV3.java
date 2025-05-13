package com.quickpick.ureca.v3.ticket.service;

import com.quickpick.ureca.v3.ticket.event.TicketPurchaseEvent;
import com.quickpick.ureca.v3.ticket.kafka.TicketEventProducer;
import com.quickpick.ureca.v3.ticket.repository.RedisStockRepository;
import com.quickpick.ureca.v3.user.repository.UserRepository;
import com.quickpick.ureca.v3.userticket.repository.UserTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceV3 {

    private final UserRepository userRepository;
    private final RedisStockRepository redisStockRepository;
    private final TicketEventProducer ticketEventProducer;
    private final UserTicketRepository userTicketRepository;

    public void purchaseTicket(final Long ticketId, final Long userId, final Long quantity) {

        if(!userRepository.existsById(userId)) {
            log.error("존재하지 않는 사용자입니다.");
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        if(userTicketRepository.existsByUserId(userId)) {
            log.error("이미 예약을 성공한 사용자입니다.");
            throw new RuntimeException("이미 예약을 성공한 사용자입니다.");
        }

        final Long result = redisStockRepository.decrease(ticketId, quantity);
        if (result == -1L) {
            throw new RuntimeException("티켓이 전부 소진되었습니다.");
        }

        final TicketPurchaseEvent ticketPurchaseEvent = TicketPurchaseEvent.builder()
                .uuid(UUID.randomUUID().toString())
                .ticketId(ticketId)
                .userId(userId)
                .quantity(quantity).build();

        ticketEventProducer.send(ticketPurchaseEvent);
    }
}
