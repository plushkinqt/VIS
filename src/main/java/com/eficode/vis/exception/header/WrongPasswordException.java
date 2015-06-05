package com.eficode.vis.exception.header;

public class WrongPasswordException extends HeaderException {

    @Override
    public String getMessage(){
        return "wrong password";
    }
}
