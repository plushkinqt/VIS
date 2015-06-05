package com.eficode.vis.wrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;


public class Wrapper<T> {
    
    private final Gson gson;
    private Header header;
    private final int version;

    public Wrapper() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        this.gson = builder
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd")
                .create();
        this.header = new Header();
        this.version = 2;
    }    
    
    public String getListReplyReplySuccessMessage(List<T> list, String service, String action) {
        header = new Header(version, service, action, true, "");
        Message message = new Message(header, list);
        return this.gson.toJson(message);
    }
    
    public String getListReplyReplyErrorMessage(List<T> list, String service, String action, String error) {
        header = new Header(version, service, action, false, error);
        Message message = new Message(header, list);
        return this.gson.toJson(message);
    }
    
    public String getObjectReplySuccessMessage(T t, String service, String action) {
        header = new Header(version, service, action, true, "");
        Message message = new Message(header, t);
        return gson.toJson(message);
    }
    
    /*public String getObjectReplyErrorMessage(T t, String service, String action, String error) {
        header = new Header(version, service, action, false, error);
        Message message = new Message(header, t);
        return gson.toJson(message);
    }*/
    
    public String getSuccessMessage(String service, String action) {
        header = new Header(version, service, action, true, "");
        Message message = new Message(header, null);
        return gson.toJson(message);
    }
    
    public String getErrorMessage(String service, String action, String error) {
        header = new Header(version, service, action, false, error);
        Message message = new Message(header, null);
        return gson.toJson(message);
    }
    
}