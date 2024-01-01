package com.game.slotmachine.controller;

import com.game.slotmachine.beans.Countdown;
import com.game.slotmachine.entities.Ticket;
import com.game.slotmachine.exception.DrawCloseException;
import com.game.slotmachine.model.dto.BetArray;
import com.game.slotmachine.model.dto.TicketDTO;
import com.game.slotmachine.model.payload.ApiResponse;
import com.game.slotmachine.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Value(
            "${drawCloseTime}"
    )
    private int drawCloseTime;
    @Autowired
    TicketService ticketService;
    @Autowired
    Countdown countdown;
    Logger logger = LoggerFactory.getLogger(TicketController.class);
    @PostMapping
    public ResponseEntity<?> addTicket(@RequestBody BetArray bets, Authentication a) throws Exception{
        if(countdown.getCountdown()<=drawCloseTime){
//            return ResponseEntity.badRequest().body("Draw close");
            throw new DrawCloseException();
        }
        logger.info("In ticket controller to add ticket");
        String authorities = a.getAuthorities().toString();
        Double balance = ticketService.addTicket(bets.getBets(),a.getName(),authorities.substring(1,authorities.length()-1));
        return new ResponseEntity<ApiResponse>(new ApiResponse(Double.toString(balance),true), HttpStatus.OK);
    }

}
