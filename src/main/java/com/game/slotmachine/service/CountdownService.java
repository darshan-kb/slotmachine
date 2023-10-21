package com.game.slotmachine.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CountdownService {
    private int varCount=120;
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
        varCount--;
        if(varCount==endCount){
            varCount=startCount;
        }
    }
}
