package com.game.slotmachine.service;

import com.game.slotmachine.entities.Game;
import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.service.sseService.SseCountdownService;
import com.game.slotmachine.service.sseService.SseResultService;
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
    @Autowired
    private SseCountdownService sseCountdownService;
    @Autowired
    private SseResultService sseResultService;

    @Scheduled(fixedRate = 1000)
    public void countDown(){
        if(varCount==startCount){
            currentGame = initGameService.gameInit();
        }
        if(varCount==0){
            sseResultService.sendResult(resultDTO);
        }
        if(varCount==7){
            resultDTO = gameService.calculateGameResult();
        }
        sseCountdownService.sendEvents(varCount);
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
