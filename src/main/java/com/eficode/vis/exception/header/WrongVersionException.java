package com.eficode.vis.exception.header;

public class WrongVersionException extends HeaderException {
    
    @Override
    public String getMessage(){
        return "wrong version";
    }
    
}
