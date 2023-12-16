package com.game.slotmachine.model.mapper;

import com.game.slotmachine.beans.ResultBean;
import com.game.slotmachine.entities.ClaimBet;
import com.game.slotmachine.entities.Ticket;
import com.game.slotmachine.model.dto.ClaimDTO;
import com.game.slotmachine.model.dto.ResultDTO;
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

    public ResultDTO ResultBeanToResultDTO(ResultBean resultBean){
        return new ResultDTO(resultBean.getSlot1List(), resultBean.getSlot2List());
    }
    public static ClaimDTO toClaimDTO(ClaimBet claim){
        return ClaimDTO.builder()
                .gameId(claim.getGame().getGameId())
                .claimId(claim.getClaimBetId())
                .claimAmount(claim.getAmount())
                .betAmount(claim.getBet().getBetAmount())
                .betNumber(claim.getBet().getBetNumber())
                .ticketId(claim.getTicket().getTicketId())
                .build();
    }
}
