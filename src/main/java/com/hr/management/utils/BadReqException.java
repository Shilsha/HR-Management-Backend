package com.hr.management.utils;

public class BadReqException extends  RuntimeException{
    public BadReqException(String message){
        super(message);
    }
}
