package com.eficode.vis.factory;

import com.eficode.vis.dao.ServerDao;
import com.eficode.vis.dao.UserDao;

public class DaoFactory {
    
    public static UserDao getUserDao() {
        return new UserDao();
    }
    
    public static ServerDao getServerDao() {
        return new ServerDao();
    }
}