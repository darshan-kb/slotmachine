package com.game.slotmachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SlotmachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlotmachineApplication.class, args);
	}

}
