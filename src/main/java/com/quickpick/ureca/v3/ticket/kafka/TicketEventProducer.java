package com.quickpick.ureca.v3.ticket.kafka;

import com.quickpick.ureca.v3.ticket.event.TicketPurchaseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketEventProducer {

    private final KafkaTemplate<String, TicketPurchaseEvent> kafkaTemplate;
    private final String TOPIC = "ticket.purchase";

    public void send(final TicketPurchaseEvent ticketPurchaseEvent) {
        kafkaTemplate.send(TOPIC, ticketPurchaseEvent);
    }
}
