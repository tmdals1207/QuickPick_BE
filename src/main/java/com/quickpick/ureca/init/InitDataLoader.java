package com.quickpick.ureca.init;

import com.quickpick.ureca.ticket.v3.domain.Ticket;
import com.quickpick.ureca.ticket.v3.repository.RedisStockRepository;
import com.quickpick.ureca.ticket.v3.repository.TicketRepository;
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
        String sql = "INSERT INTO user (id, name, age, gender, password) VALUES (?, ?, ?, ?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();

        for (long i = 1; i <= 100_000; i++) {
            batchArgs.add(new Object[]{
                    "abc" + i,
                    "user" + i,
                    (int) i,
                    "MALE", // enum은 문자열로 저장한다고 가정
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
