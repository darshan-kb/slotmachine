package com.game.slotmachine.repository;

import com.game.slotmachine.entities.Game;
import com.game.slotmachine.model.projections.GameSlot1AndSlot2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.isGameOver=true ORDER BY g.gameTimeStamp DESC LIMIT 5")
    List<GameSlot1AndSlot2> fetchTop5GameOverRow();

}
