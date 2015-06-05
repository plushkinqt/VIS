package com.eficode.vis.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="server_types")
public class ServerType implements Serializable {
    
    @Id
    @Expose
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;
    
    @Expose
    @Column(name="name", nullable=false, length=45)
    private String name;
    
    @Column(name="vzstring", nullable=false, length=255)
    private String vzstring;
    
    @Expose
    @Column(name="description", nullable=true, length=1000)
    private String description;

    public ServerType() {
        this.id = 0l;
        this.name = "";
        this.vzstring = "";
        this.description = "";
    }
    
    public ServerType(Long id, String name, String vzstring, String description) {
        this.id = id;
        this.name = name;
        this.vzstring = vzstring;
        this.description = description;
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

    public String getVzstring() {
        return vzstring;
    }

    public void setVzstring(String vzstring) {
        this.vzstring = vzstring;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
