package com.eficode.vis.wrapper;

import com.google.gson.annotations.Expose;

public class Message {
    @Expose
    private Header header;
    @Expose
    private Object data;

    public Message(Header header, Object data) {
        this.header = header;
        this.data = data;
    }
    
}
