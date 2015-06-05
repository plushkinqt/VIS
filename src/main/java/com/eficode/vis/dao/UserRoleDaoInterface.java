package com.eficode.vis.dao;

import com.eficode.vis.model.UserRole;
import java.util.List;

public interface UserRoleDaoInterface {
    
    public List<UserRole> list();
    public UserRole get(Long id);
    public void create(UserRole userRole);
    public void update(UserRole userRole);
    public Boolean delete(Long id);
}
