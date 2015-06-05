package com.eficode.vis.exception.validation;

public class DescriptionException extends ValidationException {
    
    @Override
    public String getMessage(){
        return "description did not pass validation";
    }
}
