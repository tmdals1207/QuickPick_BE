package com.quickpick.ureca.ticket.v3.kafka;

import com.quickpick.ureca.reserve.v3.domain.Reserve;
import com.quickpick.ureca.reserve.v3.domain.ReserveStatus;
import com.quickpick.ureca.ticket.v3.event.TicketPurchaseEvent;
import com.quickpick.ureca.ticket.v3.repository.ReserveRepository;
import com.quickpick.ureca.ticket.v3.repository.TicketRepository;
import com.quickpick.ureca.userticket.v3.domain.UserTicket;
import com.quickpick.ureca.userticket.v3.repository.UserTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketEventConsumer {

    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;
    private final ReserveRepository reserveRepository;

    @KafkaListener(topics = "ticket.purchase", groupId = "ticket-service")
    @Transactional
    public void consume(final TicketPurchaseEvent event) {
        log.info("ticker.purchased 이벤트 수신, 수신한 아이디 : {}", event.getUserId());

        int result = ticketRepository.decreaseStock(event.getTicketId(), 1);
        if(result == 0) {
            throw new RuntimeException("티켓 수량 부족 및 존재하지 않음");
        }

        UserTicket userTicket = UserTicket.builder()
                .ticketId(event.getTicketId())
                .userId(event.getUserId())
                .build();
        userTicketRepository.save(userTicket);

        Reserve reserve = Reserve.builder()
                .userId(event.getUserId())
                .status(ReserveStatus.SUCCESS)
                .build();
        reserveRepository.save(reserve);
    }
}
