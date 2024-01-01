package com.game.slotmachine.service;

import com.game.slotmachine.model.payload.AddTicketPayload;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountDetailService {
    @Value("${balanceURL}")
    private String balanceURL;
    @Value("${addURL}")
    private String addURL;
    @Value("${ticketErrorURL}")
    private String ticketErrorURL;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TokenService tokenService;
    Logger logger = LoggerFactory.getLogger(AccountDetailService.class);
    public double getBalance(){
        String resourseURL = balanceURL;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization",tokenService.getAuthTokenFromRequest());
        HttpEntity<String> http = new HttpEntity<>(httpHeaders);
        ResponseEntity<Double> response = restTemplate.exchange(resourseURL, HttpMethod.GET, http, Double.class);
        return response.getBody();
    }

    public void test(){
        RestTemplate restTemplate = new RestTemplate();
        String resourseURL = "http://localhost:8080/hello";
        ResponseEntity<String> response = restTemplate.getForEntity(resourseURL, String.class);
    }

    public double addTicket(double amount, long ticketId){
        logger.info("Adding ticket with ticketId "+ticketId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",tokenService.getAuthTokenFromRequest());
        HttpEntity<String> entity = new HttpEntity(new AddTicketPayload(amount, "slot-ticketId:"+ticketId),headers);

        ResponseEntity<Double> response = restTemplate.exchange(addURL, HttpMethod.POST, entity, Double.class);
        System.out.println(response.getBody());
        return response.getBody();

    }

    public double ticketError(long amount, String email){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+tokenService.getToken());
        HttpEntity<String> entity = new HttpEntity(new AddTicketPayload(amount*1.0, email),headers);

        ResponseEntity<Double> response = restTemplate.exchange(ticketErrorURL, HttpMethod.POST, entity, Double.class);
        System.out.println(response.getBody());
        return response.getBody();
    }
}
