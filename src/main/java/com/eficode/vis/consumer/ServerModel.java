package com.eficode.vis.consumer;

import com.google.gson.Gson;

public class ServerModel {
    private Long id;
    private String vzstring;
    
    public ServerModel() {
        this.id = 0l;
        this.vzstring = "";
    }
    
    public ServerModel(Long id, String vzstring) {
        this.id = id+100;
        this.vzstring = vzstring;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVzstring() {
        return this.vzstring;
    }

    public void setVzstring(String vzstring) {
        this.vzstring = vzstring;
    }
    
    public ServerModel fromJSON(String str) {
        Gson gson = new Gson();
        ServerModel server = gson.fromJson(str, ServerModel.class);
        return server;
    }
}
