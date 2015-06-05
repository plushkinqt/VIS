package com.eficode.vis.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="ustose")
@Table(name = "users_to_servers")
public class UsersToServers implements Serializable {

    @Id
    @Expose
    @ManyToOne(fetch = FetchType.EAGER, targetEntity=Server.class)
    @BatchSize(size = 10)
    @JoinColumn(name="server_id", nullable = false)
    private Server server;
    
    @Id
    @Expose
    @ManyToOne(fetch = FetchType.EAGER, targetEntity=ServerRole.class)
    @BatchSize(size = 10)
    @JoinColumn(name="server_role_id", nullable = false)
    private ServerRole serverRole;
    
    @Id
    @Expose
    @ManyToOne(fetch = FetchType.EAGER, targetEntity=User.class)
    @BatchSize(size = 10)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public UsersToServers() {
        this.server = new Server();
        this.user = new User();
        this.serverRole = new ServerRole();
    }
    
    public UsersToServers(Server server, User user, ServerRole role) {
        this.server = server;
        this.user = user;
        this.serverRole = role;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ServerRole getServerRole() {
        return serverRole;
    }

    public void setServerRole(ServerRole serverRole) {
        this.serverRole = serverRole;
    }
}