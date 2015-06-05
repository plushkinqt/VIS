package com.eficode.vis.dao;

import com.eficode.vis.model.User;
import com.eficode.vis.model.UserRest;
import java.util.List;

public interface UserDaoInterface {
    
    public List<User> list();
    public User get(Long id);
    public void create(User user);
    public void update(User user);
    public Boolean delete(Long id);
    public User CheckUsernamePassword(String username, String password);
    public List<User> listVMUsers(Long id);
    public User getPassword(String email);

}
