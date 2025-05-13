package com.quickpick.ureca.v3.init;

import com.quickpick.ureca.v3.ticket.domain.Ticket;
import com.quickpick.ureca.v3.ticket.repository.RedisStockRepository;
import com.quickpick.ureca.v3.ticket.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDataLoader implements CommandLineRunner {

    private final TicketRepository ticketRepository;
    private final RedisStockRepository redisStockRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void run(String... args) {
        String sql = "INSERT INTO user (user_id, name, age, gender, password) VALUES (?, ?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();

        for (long i = 1; i <= 10000; i++) {
            batchArgs.add(new Object[]{
                    i,
                    "abc" + i,
                    i+20,
                    "MALE",
                    "password" + i

            });
        }

        jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println("=== 10만명 유저 생성 완료 ===");


        Ticket ticket = Ticket.builder()
                .name("ticket")
                .quantity(3000L)
                .reserveTime(LocalDateTime.now())
                .startDate(LocalDate.now())
                .build();

        Ticket saveTicket = ticketRepository.save(ticket);
        redisStockRepository.setTicket(saveTicket.getId(), 3000L);
    }
}
