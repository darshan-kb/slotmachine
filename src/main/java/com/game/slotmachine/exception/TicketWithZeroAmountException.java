package com.game.slotmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Ticket with 0 amount")
public class TicketWithZeroAmountException extends Exception{
}
