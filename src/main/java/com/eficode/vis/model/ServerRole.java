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
@Table(name="server_roles")
public class ServerRole implements Serializable {
    
    @Id
    @Expose
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;
    
    @Expose
    @Column(name="name", nullable=false, length=45)
    private String name;
    
    @Expose
    @Column(name="description", nullable=true, length=1000)
    private String description;

    public ServerRole() {
        this.id = 0l;
        this.name = "";
        this.description = "";
    }
    
    public ServerRole(Long id, String name, String description) {
        this.id = id;
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ServerRole{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }
    
    
}
