
package com.eficode.vis.model;

import com.eficode.vis.exception.validation.DescriptionException;
import com.eficode.vis.exception.validation.ServerNameException;
import com.eficode.vis.exception.validation.ValidationException;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="servers")
public class Server implements Serializable {
    
    @Id
    @Expose
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;
    
    @Expose
    @Column(name="name", nullable=false, length=45)
    private String name;
    
    @Expose
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="type_id", nullable=false)
    private ServerType serverType;
    
    @Expose
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="status_id", nullable=false)
    private ServerStatus serverStatus;
    
    @Expose
    @Column(name="due_date", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @Expose
    @Column(name="description")
    private String description;
    
    @Expose
    @Column(name="created_date", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    
    @Column(name="deleted")
    private Boolean deleted;

    public Server() {
        this.id = 0l;
        this.name = "";
        this.serverType = new ServerType();
        this.serverStatus = new ServerStatus();
        this.dueDate = new Date();
        this.description = "";
        this.createdDate = new Date();
        this.deleted = false;
    }
    
    public Server(Long id, String name, ServerType serverType, ServerStatus serverStatus, Date dueDate, String description, 
            Date createdDate, Boolean deleted) throws ValidationException {
        this.id = id;
        setName(name);
        this.serverType = serverType;
        this.serverStatus = serverStatus;
        this.dueDate = dueDate;
        setDescription(description);
        this.createdDate = createdDate;
        this.deleted = deleted;
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

    public final void setName(String name) throws ServerNameException {
        if (!AbstractValidate("^[a-zA-Z0-9_-]{3,45}$", name))
             throw new ServerNameException();   
        this.name = name;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
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

    public final void setDescription(String description) throws DescriptionException {
        if(!AbstractValidate("^.{0,1000}$", description))
                throw new DescriptionException();
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Server{" + "id=" + id + ", name=" + name + ", serverType=" + serverType + ", serverStatus=" + serverStatus + ", dueDate=" + dueDate + ", description=" + description + ", createdDate=" + createdDate + ", deleted=" + deleted + '}';
    }
    
    private boolean AbstractValidate(String parameter, String toCompare){
        Pattern pattern = Pattern.compile(parameter);
        Matcher matcher = pattern.matcher(toCompare);
        if(!matcher.matches())
            return false;
        else return true;
    }
    
}
