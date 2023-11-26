package com.game.slotmachine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CountdownService {
    private int varCount=120;
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

    @Scheduled(fixedRate = 1000)
    public void countDown(){
        if(varCount==startCount){

        }
        sseService.sendEvents(varCount);
        varCount--;
        if(varCount==endCount){
            varCount=startCount;
        }
        System.out.println(varCount);
    }
}
