package com.game.slotmachine.exception;

import com.game.slotmachine.model.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

//    @ExceptionHandler(DrawCloseException.class)
//    public ResponseEntity<ApiResponse> handleDrawClosedRequest(){
//        ApiResponse ar = new ApiResponse("Draw Closed",false);
//        return new ResponseEntity<ApiResponse>(ar, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundRequest(){
        ApiResponse apiResponse = new ApiResponse("User not found", false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(InsufficientBalanceException.class)
//    public ResponseEntity<ApiResponse> handleInsufficientBalance(){
//        ApiResponse ar = new ApiResponse("Insufficient balance",false);
//        return new ResponseEntity<ApiResponse>(ar, HttpStatus.BAD_REQUEST);
//    }
}
