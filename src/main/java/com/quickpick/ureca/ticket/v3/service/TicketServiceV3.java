package com.quickpick.ureca.ticket.v3.service;

import com.quickpick.ureca.ticket.v3.event.TicketPurchaseEvent;
import com.quickpick.ureca.ticket.v3.kafka.TicketEventProducer;
import com.quickpick.ureca.ticket.v3.repository.RedisStockRepository;
import com.quickpick.ureca.user.v3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceV3 {

    private final UserRepository userRepository;
    private final RedisStockRepository redisStockRepository;
    private final TicketEventProducer ticketEventProducer;

    public void purchaseTicket(final Long ticketId, final Long userId, final Long quantity) {

        if(!userRepository.existsById(userId)) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        Long result = redisStockRepository.decrease(ticketId, quantity);
        if (result == -1L) {
            throw new RuntimeException("티켓이 전부 소진되었습니다.");
        }

        // 티켓 전부 소진시 메시지 발행
        TicketPurchaseEvent ticketPurchaseEvent = TicketPurchaseEvent.builder()
                .uuid(UUID.randomUUID().toString())
                .ticketId(ticketId)
                .userId(userId)
                .quantity(quantity).build();

        ticketEventProducer.send(ticketPurchaseEvent);
    }
}
