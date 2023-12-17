package com.game.slotmachine.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Queue;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueuePayload {
    private int slot1;
    private int slot2;
    private LocalDateTime gameTimestamp;
}
