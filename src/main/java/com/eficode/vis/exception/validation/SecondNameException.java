package com.eficode.vis.exception.validation;

public class SecondNameException extends ValidationException {
    
    @Override
    public String getMessage(){
        return "secondname did not pass validation";
    }
    
}
