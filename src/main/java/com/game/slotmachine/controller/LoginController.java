package com.game.slotmachine.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/demo")
    public String demo(Authentication a){
        return "Welcome "+a.getName();
    }
    @GetMapping("/test")
    public String test(){
        return "Welcome";
    }
}
