package com.eficode.vis.consumer;

import com.google.gson.Gson;

public class PasswordModel {
    
    private Long id;
    private String password;
    
    public PasswordModel() {
        this.id = 0l;
        this.password = "";
    }
    
    public PasswordModel(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public PasswordModel fromJSON(String str) {
        Gson gson = new Gson();
        PasswordModel password = gson.fromJson(str, PasswordModel.class);
        return password;
    }
}
