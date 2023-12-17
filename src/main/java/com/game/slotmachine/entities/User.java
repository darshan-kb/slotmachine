package com.game.slotmachine.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String role;
    private String username;
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Ticket> tickets;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ClaimBet> claimBets;

    public User(String username, String email, String role){
        this.username=username;
        this.email=email;
        this.role=role;
    }
}
