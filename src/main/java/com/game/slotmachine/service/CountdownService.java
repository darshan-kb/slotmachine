package com.game.slotmachine.service;

import com.game.slotmachine.beans.Countdown;
import com.game.slotmachine.beans.ResultQueue;
import com.game.slotmachine.entities.Game;
import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.service.sseService.SseCountdownService;
import com.game.slotmachine.service.sseService.SseQueueService;
import com.game.slotmachine.service.sseService.SseResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CountdownService {
//    @Value(
//            ("${startCount}")
//    )
//    private int varCount;
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
    Countdown countdown;
    @Autowired
    InitGameService initGameService;
    @Autowired
    GameService gameService;
    @Autowired
    private SseCountdownService sseCountdownService;
    @Autowired
    private SseResultService sseResultService;
    @Autowired
    private SseQueueService sseQueueService;
    @Autowired
    private ResultQueue resultQueue;

    @Scheduled(fixedRate = 1000)
    public void countDown(){
        if(countdown.getCountdown()==startCount){
            currentGame = initGameService.gameInit();
            sseQueueService.sendQueue();
        }
        if(countdown.getCountdown()==7){
            resultDTO = gameService.calculateGameResult();
        }
        if(countdown.getCountdown()==0){
            sseResultService.sendResult(resultDTO);
        }
        sseCountdownService.sendEvents(countdown.getCountdown());
        countdown.decrement();
        if(countdown.getCountdown()==endCount){
            countdown.reset();
            gameService.markGameAsOver(getCurrentGame());
        }
        System.out.println(countdown.getCountdown());
    }

    public Game getCurrentGame(){
        return currentGame;
    }

    public void setCurrentGame(Game game){
        currentGame = game;
    }

    public ResultDTO getResultDTO(){
        return resultDTO;
    }
}
