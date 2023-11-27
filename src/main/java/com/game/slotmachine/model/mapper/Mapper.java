package com.game.slotmachine.model.mapper;

import com.game.slotmachine.entities.Ticket;
import com.game.slotmachine.model.dto.TicketDTO;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public TicketDTO ticketToTicketDTO(Ticket ticket){
        return TicketDTO.builder()
                .ticketId(ticket.getTicketId())
                .gameId(ticket.getGame().getGameId())
                .amount(ticket.getTicketAmount())
                .timestamp(ticket.getTicketTimeStamp())
                .build();
    }
}
