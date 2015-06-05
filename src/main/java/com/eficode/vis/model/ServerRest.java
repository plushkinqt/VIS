
package com.eficode.vis.model;

import java.util.Calendar;
import java.util.Date;

public class ServerRest {
    
    private Long id;
    private String name;
    private String serverType;
    private String serverStatus;
    private Date dueDate;
    private String description;
    private Date createdDate;

    public ServerRest() {
        this.id = 0l;
        this.name = "";
        this.serverType = "";
        this.serverStatus = "";
        this.dueDate = new Date();
        this.description = "";
        this.createdDate = new Date();
    }
    
    public ServerRest(String name, long serverType, String description) {
        this.id = 0l;
        this.name = name;
        this.serverType = "";
        this.serverStatus = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 30);
        this.dueDate = calendar.getTime();
        this.description = description;
        this.createdDate = new Date();
    }
    
    public ServerRest(Long id, String name, String serverType, String serverStatus, Date dueDate, String description, 
            Date createdDate) {
        this.id = id;
        this.name = name;
        this.serverType = serverType;
        this.serverStatus = serverStatus;
        this.dueDate = dueDate;
        this.description = description;
        this.createdDate = createdDate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
  
}
