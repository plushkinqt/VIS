package com.eficode.vis.wrapper;

import com.eficode.vis.dao.UserDao;
import com.eficode.vis.exception.header.WrongPasswordException;
import com.eficode.vis.exception.header.WrongUserIdException;
import com.eficode.vis.exception.header.WrongVersionException;
import com.eficode.vis.model.User;

public class IncomingWrapper {

    private static final int VERSION = 2;
    
    public void HandleMessage(int version,long userId, String password, String message) throws WrongVersionException,
            WrongUserIdException, WrongPasswordException {
        UserDao userDao = new UserDao();
        User user = userDao.get(userId);       
        if (VERSION != version)
            throw new WrongVersionException();
        else if(user == null)
            throw new WrongUserIdException();
        else if(!user.getPassword().equals(password))
            throw new WrongPasswordException();
    }
    
    public void CheckUnauthorizesMessage(int version, String message) throws WrongVersionException{
        if (VERSION != version)
            throw new WrongVersionException();
    }
   
}

