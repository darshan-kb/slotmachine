package com.game.slotmachine.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SseCountdownPayload {
    private int payloadValue;
}
