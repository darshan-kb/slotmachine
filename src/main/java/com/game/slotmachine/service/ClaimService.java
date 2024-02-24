package com.game.slotmachine.service;

import com.game.slotmachine.beans.ResultBean;
import com.game.slotmachine.entities.*;
import com.game.slotmachine.model.dto.ClaimDTO;
import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.model.mapper.Mapper;
import com.game.slotmachine.model.payload.AccountRedeemClaimPayload;
import com.game.slotmachine.repository.ClaimBetRepository;
import com.game.slotmachine.repository.GameRepository;
import com.game.slotmachine.repository.TicketRepository;
import com.game.slotmachine.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaimService {
    @Autowired
    ClaimBetRepository claimBetRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpRequestService httpRequestService;
    private static Logger LOGGER = LoggerFactory.getLogger(ClaimService.class);
    @Transactional
    public void addClaim(ResultDTO resultBean, Game currentGame){
        LOGGER.info("Adding claim for this result {}",resultBean);
        Game game = gameRepository.findById(currentGame.getGameId()).orElseThrow(()->new RuntimeException());
        LOGGER.info(Long.toString(game.getGameId())+" "+resultBean.toString());
        //List<Ticket> tickets = ticketRepository.findAllByGame(game);
        List<Ticket> tickets = game.getTickets();
        LOGGER.info(tickets.toString());
        if(tickets==null)
            return;
        for(Ticket ticket : tickets){
            List<Bet> bets = ticket.getBets();
            for(Bet bet : bets){
                LOGGER.info(bet.toString());
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

    @Transactional
    public List<ClaimDTO> getClaim(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        List<ClaimBet> claimBets = claimBetRepository.findByUserAndClaimBetIsClaimed(user).orElseThrow(() -> new UsernameNotFoundException("User not found in Claim Bets repository"));
        return claimBets.stream().map(Mapper::toClaimDTO).toList();
    }

    @Transactional
    public double redeemClaim(long claimId, String email){
        LOGGER.info(claimId+" "+email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        ClaimBet claimBet = claimBetRepository.findById(claimId).orElseThrow(() -> new IllegalStateException("ClaimId not found"));
        if(claimBet.isClaimed())
            throw new IllegalStateException("Already claimed");
        AccountRedeemClaimPayload accountRedeemClaimPayload = new AccountRedeemClaimPayload(claimBet.getAmount(), "slot-claimId:"+claimId);
        ResponseEntity<Double> response = httpRequestService.sendPostRequestToRedeemClaim(accountRedeemClaimPayload);
        if(response.getStatusCode()!= HttpStatus.OK)
            throw new RuntimeException("unable to claim");
        claimBet.setClaimed(true);
        claimBetRepository.save(claimBet);
        return response.getBody();
    }
}
