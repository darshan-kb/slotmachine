package com.game.slotmachine.controller;

import com.game.slotmachine.beans.ResultQueue;
import com.game.slotmachine.model.payload.QueuePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

@RestController
public class QueueController {
    @Autowired
    ResultQueue resultQueue;

    @GetMapping("/queue")
    public ResponseEntity<Queue<QueuePayload>> getResultQueue(){
        return new ResponseEntity<>(resultQueue.getQueue(), HttpStatus.OK);
    }
}
