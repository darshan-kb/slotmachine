package com.game.slotmachine.service;

import com.game.slotmachine.beans.ResultBean;
import com.game.slotmachine.entities.Bet;
import com.game.slotmachine.entities.ClaimBet;
import com.game.slotmachine.entities.Game;
import com.game.slotmachine.entities.Ticket;
import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.repository.ClaimBetRepository;
import com.game.slotmachine.repository.GameRepository;
import com.game.slotmachine.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClaimService {
    @Autowired
    ClaimBetRepository claimBetRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TicketRepository ticketRepository;
    Logger logger = LoggerFactory.getLogger(ClaimService.class);
    @Transactional
    public void addClaim(ResultDTO resultBean, Game currentGame){
        Game game = gameRepository.findById(currentGame.getGameId()).orElseThrow(()->new RuntimeException());
        logger.info(Long.toString(game.getGameId())+" "+resultBean.toString());
        //List<Ticket> tickets = ticketRepository.findAllByGame(game);
        List<Ticket> tickets = game.getTickets();
        logger.info(tickets.toString());
        if(tickets==null)
            return;
        for(Ticket ticket : tickets){
            List<Bet> bets = ticket.getBets();
            for(Bet bet : bets){
                logger.info(bet.toString());
                if(bet.getBetNumber() == resultBean.slot1().get(11)) {
                    claimBetRepository.save(ClaimBet.builder()
                                    .claimed(false)
                                    .user(ticket.getUser())
                                    .amount(resultBean.slot2().get(2)*10*bet.getBetAmount())
                                    .bet(bet)
                                    .ticket(ticket)
                                    .game(game)
                            .build());
                }
            }
        }
    }
}
