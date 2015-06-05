package com.eficode.vis.exception.validation;

public class ServerNameException extends ValidationException{
    
    @Override
    public String getMessage(){
        return "server name did not pass validation";
    }
    
}
