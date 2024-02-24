package com.game.slotmachine.controller;

import com.game.slotmachine.beans.Countdown;
import com.game.slotmachine.model.payload.ApiResponse;
import com.game.slotmachine.model.payload.FixResult;
import com.game.slotmachine.service.CountdownService;
import com.game.slotmachine.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private GameService gameService;

    @Autowired
    private Countdown countdown;

    @PutMapping(value = "/fix")
    public ResponseEntity<?> fixResult(@RequestBody FixResult fixResult){
        if(countdown.getCountdown()<10 && countdown.getCountdown()>0){
            return ResponseEntity.ok(gameService.fixResult(fixResult));
        }
        else{
            ApiResponse apiResponse = new ApiResponse("Fix Result window closed!",false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
