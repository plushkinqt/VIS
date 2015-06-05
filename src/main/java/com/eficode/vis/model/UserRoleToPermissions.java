package com.eficode.vis.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "roles_to_permissions")
public class UserRoleToPermissions implements Serializable{
    
    @Id
    @Expose
    @ManyToOne(targetEntity=UserRole.class)
    @JoinColumn(name="role_id", nullable = false)
    private UserRole userRole;
    
    @Id
    @Expose
    @ManyToOne(targetEntity=UserPermission.class)
    @JoinColumn(name="permission_id", nullable = false)
    private UserPermission userPermission;

    public UserRoleToPermissions() {
        this.userRole = new UserRole();
        this.userPermission = new UserPermission();
    }
    
    public UserRoleToPermissions(UserRole userRole, UserPermission userPermission) {
        this.userRole = userRole;
        this.userPermission = userPermission;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserPermission getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(UserPermission userPermission) {
        this.userPermission = userPermission;
    }

    @Override
    public String toString() {
        return "UserRoleToPermissions{" + "userRole=" + userRole + ", userPermission=" + userPermission + '}';
    }
    
    
}
