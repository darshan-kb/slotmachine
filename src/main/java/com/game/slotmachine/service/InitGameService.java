package com.game.slotmachine.service;

import com.game.slotmachine.beans.CachedTotalBetsAmountMap;
import com.game.slotmachine.entities.Game;
import com.game.slotmachine.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InitGameService {
    @Autowired
    private GameRepository gamerepo;

    @Autowired
    CachedTotalBetsAmountMap map;
    @Transactional
    public Game gameInit(){
        LocalDateTime startTime = LocalDateTime.now();
        map.initializeValueMap();
        return gamerepo.save(new Game(-1,-1,startTime,0,0,false));
    }

}
