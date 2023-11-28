package com.game.slotmachine.service;

import com.game.slotmachine.entities.Game;
import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.service.sseService.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CountdownService {
    @Value(
            ("${startCount}")
    )
    private int varCount;
    @Autowired
    private SseService sseService;
    @Value(
            ("${startCount}")
    )
    private int startCount;
    @Value(
            ("${endCount}")
    )
    private int endCount;
    private Game currentGame;
    private ResultDTO resultDTO;

    @Autowired
    InitGameService initGameService;
    @Autowired
    GameService gameService;

    @Scheduled(fixedRate = 1000)
    public void countDown(){
        if(varCount==startCount){
            currentGame = initGameService.gameInit();
        }
        if(varCount==0){
            sseService.sendResult(resultDTO);
        }
        if(varCount==7){
            resultDTO = gameService.calculateGameResult();
        }
        sseService.sendEvents(varCount);
        varCount--;
        if(varCount==endCount){
            varCount=startCount;
        }
        System.out.println(varCount);
    }

    public Game getCurrentGame(){
        return currentGame;
    }

    public void setCurrentGame(Game game){
        currentGame = game;
    }
}
