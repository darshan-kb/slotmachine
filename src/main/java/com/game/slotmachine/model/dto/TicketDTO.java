package com.game.slotmachine.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Builder
@ToString
@Getter
public class TicketDTO {
    private long ticketId;
    private long gameId;
    private double amount;
    private LocalDateTime timestamp;
}
