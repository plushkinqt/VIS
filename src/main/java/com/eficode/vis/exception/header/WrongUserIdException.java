package com.eficode.vis.exception.header;

public class WrongUserIdException extends HeaderException{
       
    @Override
    public String getMessage(){
        return "wrong userId";
    }
    
}
