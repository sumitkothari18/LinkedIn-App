package com.example.LinkedIn.user_service.exceptions;

public class BadExceptionError extends RuntimeException{

    public BadExceptionError(String message)
    {
        super(message);
    }
}
