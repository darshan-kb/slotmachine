package com.game.slotmachine.service;

import com.game.slotmachine.beans.CachedTotalBetsAmountMap;
import com.game.slotmachine.entities.Bet;
import com.game.slotmachine.entities.Ticket;
import com.game.slotmachine.model.dto.TicketDTO;
import com.game.slotmachine.model.mapper.Mapper;
import com.game.slotmachine.repository.BetRepository;
import com.game.slotmachine.repository.GameRepository;
import com.game.slotmachine.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private TicketRepository ticketRepository;
    private GameRepository gameRepository;
    private BetRepository betRepository;
    private CountdownService countdownService;
    private CachedTotalBetsAmountMap betMap;
    private Mapper mapper;
//    private Logger logger;
    @Transactional
    public TicketDTO addTicket(List<Double> bets){

        Ticket ticket = Ticket.builder()
                .ticketTimeStamp(LocalDateTime.now())
                .ticketAmount(bets.stream().reduce(0.0,(a,b)->a+b))
                .game(countdownService.getCurrentGame())
                .build();
        ticketRepository.save(ticket);
        for(int i=0;i<12;i++){
            if(bets.get(i)!=0) {
                betRepository.save(new Bet(i + 1, bets.get(i), ticket));
                betMap.addAmountToBet(i + 1, bets.get(i));
            }
        }
        System.out.println(bets);
        return mapper.ticketToTicketDTO(ticket);
    }
}
