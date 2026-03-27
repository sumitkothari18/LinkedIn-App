package com.example.LinkedIn.user_service.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    String error;
    HttpStatus statusCode;
    LocalDateTime timeStamp;

    public ApiError()
    {
        this.timeStamp=LocalDateTime.now();
    }

    public ApiError(String error,HttpStatus statusCode)
    {
        this.error=error;
        this.statusCode=statusCode;
    }
}
