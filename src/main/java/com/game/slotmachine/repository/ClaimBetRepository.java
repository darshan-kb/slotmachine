package com.game.slotmachine.repository;

import com.game.slotmachine.entities.ClaimBet;
import com.game.slotmachine.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClaimBetRepository extends JpaRepository<ClaimBet, Long> {
    @Query("SELECT c from ClaimBet c Where c.user=:user AND c.claimed=false")
    Optional<List<ClaimBet>> findByUserAndClaimBetIsClaimed(User user);
}
