package com.quickpick.ureca.common.init;

import com.quickpick.ureca.ticket.repository.TicketRepository;
import com.quickpick.ureca.ticket.v2.domain.Ticket;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/init")
public class InitController {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @PostMapping
    public String initializeData(
            @RequestParam(defaultValue = "20") int ticketCount,
            @RequestParam(defaultValue = "1000") int userCount,
            @RequestParam(defaultValue = "1") long ticketId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime reserveDate
    ) {
        ticketRepository.deleteAll();
        userRepository.deleteAll();

        // 티켓 생성
        Ticket ticket = Ticket.builder()
//                .ticketId(ticketId)
                .name("테스트 티켓")
                .quantity(ticketCount)
                .startDate(startDate != null ? startDate : LocalDateTime.now().plusDays(1))
                .reserveDate(reserveDate != null ? reserveDate : LocalDateTime.now())
                .build();
        ticketRepository.save(ticket);

        // 유저 생성
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= userCount; i++) {
            User user = User.builder()
                    .id("user" + i)
                    .password("pw" + i)
                    .name("User" + i)
                    .age("20")
                    .gender("M")
                    .build();
            users.add(user);
        }
        userRepository.saveAll(users);

        return "초기화 완료: 티켓 1개(" + ticketCount + "개 수량), 유저 " + userCount + "명 생성";
    }
}
