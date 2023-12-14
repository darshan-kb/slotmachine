package com.game.slotmachine.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ticketId;
    private LocalDateTime ticketTimeStamp;
    private double ticketAmount;
    @ManyToOne
    private Game game;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Bet> bets;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<ClaimBet> claimBets;
}
