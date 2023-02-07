package com.ca.exception;

public class BadReqException extends RuntimeException{

    public BadReqException(String message){
        super(message);
    }
}
