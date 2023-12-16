package com.game.slotmachine.controller;

import com.game.slotmachine.model.dto.ClaimDTO;
import com.game.slotmachine.model.payload.RedeemClaimPayload;
import com.game.slotmachine.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class ClaimController {
    @Autowired
    private ClaimService claimService;
    @GetMapping("/api/claims")
    public ResponseEntity<List<ClaimDTO>> getClaim(Principal p){
        return new ResponseEntity(claimService.getClaim(p.getName()), HttpStatus.OK);
    }

    @PostMapping("/api/claims")
    public ResponseEntity<Double> claimReward(@RequestBody RedeemClaimPayload redeemClaimPayload, Principal p){
        return new ResponseEntity<>(claimService.redeemClaim(redeemClaimPayload.getClaimId(),p.getName()),HttpStatus.OK);
    }
}
