package com.game.slotmachine.controller;

import com.game.slotmachine.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    GameService gameService;
    @GetMapping("/game/lastgame")
    public ResponseEntity<?> getLastFinisedGame(){
        return new ResponseEntity(gameService.getLastFinisedGame(), HttpStatus.OK);
    }

}
