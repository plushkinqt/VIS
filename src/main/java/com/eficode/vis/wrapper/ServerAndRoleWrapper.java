package com.eficode.vis.wrapper;

import com.eficode.vis.model.Server;
import com.eficode.vis.model.ServerRest;
import com.eficode.vis.model.ServerRole;

public class ServerAndRoleWrapper {

      private Server server;
      private ServerRole role;

    public ServerAndRoleWrapper(Server server, ServerRole role) {
        this.server = server;
        this.role = role;
    }
    
    public ServerAndRoleWrapper() {
        this.server = new Server();
        this.role = new ServerRole();
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ServerRole getRole() {
        return role;
    }

    public void setRole(ServerRole role) {
        this.role = role;
    }
      
      
}
