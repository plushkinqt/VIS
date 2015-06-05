package com.eficode.vis.dao;

import static com.eficode.vis.dao.AbstractDao.sessionFactory;
import com.eficode.vis.model.User;
import com.eficode.vis.model.UserRole;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserDao extends AbstractDao<User> implements UserDaoInterface {
    
    public UserDao() {
        super("User");
    }
    
    public User CheckUsernamePassword(String username, String password){
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from " + User.class.getName() + " p where p.username=:username and p.password=:password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }
    
    public List<User> listVMUsers(Long id){//also here listMyVMs
        List<User> objects = new ArrayList<User>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from UsersToServers p where p.server.id=:id");
        query.setParameter("id", id);
        objects = query.list();
        session.close();
        return objects;
    }
    
    public List<User> listNotVMUsers(Long id){
        List<User> objects = new ArrayList<User>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from User p where deleted = 0 and p not in (select q.user from"
                + " UsersToServers q where q.server.id =:ID)");
        query.setParameter("ID", id);
        objects = query.list();
        session.close();
        return objects;
    }
    
    public User getPassword(String email){
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from " + User.class.getName() + " p where p.email=:EMAIL and deleted = 0");
        query.setParameter("EMAIL", email);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }
    
    public List<UserRole> listUsersRoles(){
        List<UserRole> roles = new ArrayList<UserRole>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from UserRole p");
        roles = query.list();
        session.close();
        return roles;
    }
}
