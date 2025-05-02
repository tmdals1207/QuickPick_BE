package com.quickpick.ureca.common.init;

import com.quickpick.ureca.ticket.v1.domain.TicketV1;
import com.quickpick.ureca.ticket.v1.repository.TicketRepositoryV1;
import com.quickpick.ureca.user.v1.domain.UserV1;
import com.quickpick.ureca.user.v1.repository.UserRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/init")
public class InitController {

    private final TicketRepositoryV1 ticketRepository;
    private final UserRepositoryV1 userRepository;

    @PostMapping
    public String initializeData(
            @RequestParam(defaultValue = "1000") int ticketCount,
            @RequestParam(defaultValue = "10000") int userCount,
            @RequestParam(defaultValue = "1") long ticketId
    ) {
        ticketRepository.deleteAll();
        userRepository.deleteAll();

        // 티켓 생성
        TicketV1 ticket = TicketV1.builder()
                .name("테스트 티켓")
                .quantity(ticketCount)
                .build();
        ticketRepository.save(ticket);

        // 유저 생성
        List<UserV1> users = new ArrayList<>();
        for (int i = 1; i <= userCount; i++) {
            UserV1 user = UserV1.builder()
                    .id("user" + i)
                    .build();
            users.add(user);
        }
        userRepository.saveAll(users);

        return "초기화 완료: 티켓 1개(" + ticketCount + "개 수량), 유저 " + userCount + "명 생성";
    }
}