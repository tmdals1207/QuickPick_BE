package com.quickpick.ureca.v3.ticket.kafka;

import com.quickpick.ureca.v3.reserve.domain.Reserve;
import com.quickpick.ureca.v3.reserve.domain.ReserveStatus;
import com.quickpick.ureca.v3.ticket.event.TicketPurchaseEvent;
import com.quickpick.ureca.v3.ticket.repository.ReserveRepository;
import com.quickpick.ureca.v3.ticket.repository.TicketRepository;
import com.quickpick.ureca.v3.userticket.domain.UserTicket;
import com.quickpick.ureca.v3.userticket.repository.UserTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketEventConsumer {

    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;
    private final ReserveRepository reserveRepository;

    @Transactional
    @KafkaListener(topics = "ticket.purchase", groupId = "ticket-service", concurrency = "3")
    public void consume(final TicketPurchaseEvent event, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("ticker.purchased 이벤트 수신, 수신한 아이디 : {}", event.getUserId());
        log.info("Consumed message from partition [{}] by thread [{}], userId: {}",
                partition,
                Thread.currentThread().getName(),
                event.getUserId());

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
