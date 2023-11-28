package com.game.slotmachine.controller;

import com.game.slotmachine.entities.Ticket;
import com.game.slotmachine.model.dto.BetArray;
import com.game.slotmachine.model.dto.TicketDTO;
import com.game.slotmachine.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    Logger logger = LoggerFactory.getLogger(TicketController.class);
    @PostMapping

    public ResponseEntity<?> addTicket(@RequestBody BetArray bets){
        TicketDTO ticketDTO = ticketService.addTicket(bets.getBets());
        return new ResponseEntity<TicketDTO>(ticketDTO, HttpStatus.OK);
    }

}
