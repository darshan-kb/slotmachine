package com.game.slotmachine.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;
@Getter
@Setter
@AllArgsConstructor
public class QueuePayload {
    private Queue<int[]> resultQueue;
}
