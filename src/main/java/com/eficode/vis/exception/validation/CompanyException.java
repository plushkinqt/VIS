package com.eficode.vis.exception.validation;

public class CompanyException extends ValidationException{
    
    @Override
    public String getMessage(){
        return "company name did not pass validation";
    }
}
