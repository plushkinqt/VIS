package com.eficode.vis.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "server_roles_to_server_permissions")
public class ServerRolesToServerPermissions implements Serializable{

    @Id
    @Expose
    @ManyToOne(targetEntity=ServerRole.class)
    @JoinColumn(name="server_role_id", nullable = false)
    private ServerRole serverRole;
    
    @Id
    @Expose
    @ManyToOne(targetEntity=ServerPermission.class)
    @JoinColumn(name="server_permission_id", nullable = false)
    private ServerPermission serverPermission;

    public ServerRolesToServerPermissions() {
        this.serverRole = new ServerRole();
        this.serverPermission = new ServerPermission();
    }
    
    public ServerRolesToServerPermissions(ServerRole serverRole, ServerPermission serverPermission) {
        this.serverRole = serverRole;
        this.serverPermission = serverPermission;
    }

    public ServerRole getServerRole() {
        return serverRole;
    }

    public void setServerRole(ServerRole serverRole) {
        this.serverRole = serverRole;
    }

    public ServerPermission getServerPermission() {
        return serverPermission;
    }

    public void setServerPermission(ServerPermission serverPermission) {
        this.serverPermission = serverPermission;
    }

    @Override
    public String toString() {
        return "ServerRolesToServerPermissions{" + "serverRole=" + serverRole + ", serverPermission=" + serverPermission + '}';
    }
    
    
}
