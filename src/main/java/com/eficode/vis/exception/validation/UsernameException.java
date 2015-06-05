package com.eficode.vis.exception.validation;

public class UsernameException extends ValidationException{
    
    @Override
    public String getMessage(){
        return "username did not pass validation";
    }
    
}
