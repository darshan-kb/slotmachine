package com.game.slotmachine.model.projections;

import java.time.LocalDateTime;

public interface GameSlot1AndSlot2 {
    public int getSlot1();
    public int getSlot2();
    public LocalDateTime getGameTimeStamp();
}
