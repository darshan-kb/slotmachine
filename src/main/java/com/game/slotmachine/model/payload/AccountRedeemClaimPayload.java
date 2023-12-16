package com.game.slotmachine.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRedeemClaimPayload {
    private String email;
    private double claimAmount;
    private String id;
}
