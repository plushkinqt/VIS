package com.eficode.vis.exception.validation;

public class PasswordException extends ValidationException{
    
    @Override
    public String getMessage(){
        return "password did not pass validation";
    }
    
}
