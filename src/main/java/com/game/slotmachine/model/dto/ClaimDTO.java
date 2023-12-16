package com.game.slotmachine.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ClaimDTO {
    private long gameId;
    private long ticketId;
    private long claimId;
    private int betNumber;
    private double betAmount;
    private double claimAmount;
}
