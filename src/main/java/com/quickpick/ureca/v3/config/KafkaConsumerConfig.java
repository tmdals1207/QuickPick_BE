package com.quickpick.ureca.v3.config;

import com.quickpick.ureca.v3.ticket.event.TicketPurchaseEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TicketPurchaseEvent> batchFactory(
            ConsumerFactory<String, TicketPurchaseEvent> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, TicketPurchaseEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true); // 핵심
        factory.setConcurrency(3);
        return factory;
    }
}
