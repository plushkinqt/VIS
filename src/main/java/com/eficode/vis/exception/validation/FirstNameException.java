package com.eficode.vis.exception.validation;

public class FirstNameException extends ValidationException{
    
    @Override
    public String getMessage(){
        return "firstname did not pass validation";
    }
    
}
