package com.game.slotmachine.controller;

import com.game.slotmachine.beans.Countdown;
import com.game.slotmachine.beans.ResultBean;
import com.game.slotmachine.model.payload.ResultPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {
    @Value("${endCount}")
    private int endCount;
    @Autowired
    ResultBean resultBean;
    @Autowired
    Countdown countdown;
    @GetMapping("/result")
    public ResponseEntity<ResultPayload> sendResult(){
        if(countdown.getCountdown()-30>endCount && countdown.getCountdown()<0)
            return new ResponseEntity<>(new ResultPayload(resultBean.getSlot1List(),resultBean.getSlot2List(),true), HttpStatus.OK);
        return new ResponseEntity<>(new ResultPayload(resultBean.getSlot1List(),resultBean.getSlot2List(),false), HttpStatus.OK);
    }
}
