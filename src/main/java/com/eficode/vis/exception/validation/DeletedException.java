package com.eficode.vis.exception.validation;

public class DeletedException extends ValidationException{
    
    @Override
    public String getMessage(){
        return "deleted field did not pass validation";
    }
    
}
