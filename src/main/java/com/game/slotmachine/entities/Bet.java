package com.game.slotmachine.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long betId;
    private int betNumber;
    private double betAmount;
    @ManyToOne
    private Ticket ticket;

    public Bet(int betNumber, double betAmount, Ticket ticket) {
        this.betNumber = betNumber;
        this.betAmount = betAmount;
        this.ticket = ticket;
    }
}
