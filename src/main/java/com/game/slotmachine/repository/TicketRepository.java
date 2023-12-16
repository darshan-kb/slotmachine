package com.game.slotmachine.repository;

import com.game.slotmachine.entities.Game;
import com.game.slotmachine.entities.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByGame(Game game);
}
