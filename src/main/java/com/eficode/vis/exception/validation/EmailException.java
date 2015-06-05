package com.eficode.vis.exception.validation;

public class EmailException extends ValidationException{
    
    @Override
    public String getMessage(){
        return "email did not pass validation";
    }
}
