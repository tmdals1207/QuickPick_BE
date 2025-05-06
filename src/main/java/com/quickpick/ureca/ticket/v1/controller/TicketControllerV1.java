package com.quickpick.ureca.ticket.v1.controller;

import com.quickpick.ureca.reserve.v1.service.ReserveServiceV1;
import com.quickpick.ureca.ticket.v1.domain.Ticket;
import com.quickpick.ureca.ticket.v1.dto.TicketReserveResponse;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-reserve")
@Slf4j
public class TicketControllerV1 {

    private final ReserveServiceV1 reserveServiceV1;
    private final UserRepository userRepository;

    public TicketControllerV1(ReserveServiceV1 reserveServiceV1, UserRepository userRepository) {
        this.reserveServiceV1 = reserveServiceV1;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> reserve(@RequestParam Long userId, @RequestParam Long ticketId) {
        try {
            reserveServiceV1.reserveTicket(userId, ticketId);
            return ResponseEntity.ok("예약 성공");
        } catch (Exception e) {
            log.error("예약 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약 실패: " + e.getMessage());
        }
    }

//    @PostMapping
//    public ResponseEntity<TicketReserveResponse> reserve(@RequestParam Long userId, @RequestParam Long ticketId) {
//        try {
//            Ticket ticket = reserveServiceV1.reserveTicket(userId, ticketId);
//            User user = userRepository.findById(userId).orElseThrow();
//            return ResponseEntity.ok(TicketReserveResponse.of(ticket, user));
//        } catch (Exception e) {
//            log.error("예약 실패: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }

}
