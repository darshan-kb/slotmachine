package com.game.slotmachine.service;

import com.game.slotmachine.beans.CachedTotalBetsAmountMap;
import com.game.slotmachine.entities.Bet;
import com.game.slotmachine.entities.Ticket;
import com.game.slotmachine.entities.User;
import com.game.slotmachine.exception.InsufficientBalanceException;
import com.game.slotmachine.exception.TicketWithZeroAmountException;
import com.game.slotmachine.exception.UserNotFoundException;
import com.game.slotmachine.model.dto.TicketDTO;
import com.game.slotmachine.model.mapper.Mapper;
import com.game.slotmachine.repository.BetRepository;
import com.game.slotmachine.repository.GameRepository;
import com.game.slotmachine.repository.TicketRepository;
import com.game.slotmachine.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private AccountDetailService accountDetailService;
    private UserRepository userRepository;
//    private Logger logger;
    @Transactional
    public double addTicket(List<Double> bets, String email, String authorities) throws Exception{
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            return userRepository.save(new User(email,email,authorities));
        });
        double totalAmount = bets.stream().reduce(0.0,(a,b)->a+b);
        if(totalAmount==0.0){
            throw new TicketWithZeroAmountException();
        }
        System.out.println(countdownService.getCurrentGame().getGameId());
        Ticket ticket = Ticket.builder()
                .ticketTimeStamp(LocalDateTime.now())
                .ticketAmount(totalAmount)
                .game(countdownService.getCurrentGame())
                .user(user)
                .build();
        Ticket savedTicket = ticketRepository.save(ticket);
        for(int i=0;i<12;i++){
            if(bets.get(i)!=0) {
                betRepository.save(new Bet(i + 1, bets.get(i), ticket));
                betMap.addAmountToBet(i + 1, bets.get(i));
            }
        }
        double balance;
        try{
            balance = accountDetailService.addTicket(totalAmount, savedTicket.getTicketId());
        }
        catch(Exception e){
            throw new InsufficientBalanceException();
        }

        return balance;
    }
}
