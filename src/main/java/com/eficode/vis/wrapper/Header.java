package com.eficode.vis.wrapper;

import com.google.gson.annotations.Expose;

public class Header {
    @Expose
    private int version;
    @Expose
    private String service;
    @Expose
    private String action;
    @Expose
    private boolean result;
    @Expose
    private String message;

    public Header() {
        this.version = 0;
        this.service = "";
        this.action = "";
        this.result = false;
        this.message = "";
    }

    public Header(int version, String service, String action, boolean result, String errorMessage) {
        this.version = version;
        this.service = service;
        this.action = action;
        this.result = result;
        this.message = errorMessage;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return message;
    }

    public void setErrorMessage(String message) {
        this.message = message;
    }
    
}
