package com.eficode.vis.exception.validation;

public class PhoneException extends ValidationException{
    
    @Override
    public String getMessage(){
        return "phone did not pass validation";
    }
}
