package com.eficode.vis.dao;

import com.eficode.vis.model.Server;
import com.eficode.vis.model.ServerRest;
import com.eficode.vis.model.ServerRole;
import com.eficode.vis.model.User;
import java.util.List;

public interface ServerDaoInterface {
    
    public List<Server> list();
    public Server get(Long id);
    public void create(Server server, User user, ServerRole role);
    public void update(Server server);
    public Boolean delete(Long id);
    public List<Server> listUserServers(long id);
    
    
}
