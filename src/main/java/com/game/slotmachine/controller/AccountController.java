package com.game.slotmachine.controller;

import com.game.slotmachine.service.AccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccountController {
    @Autowired
    AccountDetailService accountDetailService;

    @GetMapping("/account/balance")
    public ResponseEntity<Double> getAccountBalance(Principal p){
        double balance = accountDetailService.getBalance(p.getName());
        return ResponseEntity.ok(balance);
    }
//    @PostMapping("/account")
//    public ResponseEntity<UserDTO> createAccount(@RequestBody UserDTO user){
//        return new ResponseEntity<UserDTO>(signUpService.signUpUser(user), HttpStatus.OK);
//    }
}
