package com.eficode.vis.dao;

import static com.eficode.vis.dao.AbstractDao.sessionFactory;
import com.eficode.vis.model.Server;
import com.eficode.vis.model.ServerRole;
import com.eficode.vis.model.ServerType;
import com.eficode.vis.model.User;
import com.eficode.vis.model.UsersToServers;
import com.eficode.vis.service.Servers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class ServerDao extends AbstractDao<Server> implements ServerDaoInterface {
    
    public ServerDao() {
        super("Server");
    }
    
    @Override
    public void create(Server server, User user, ServerRole role) {
        this.create(server);
        UsersToServers usersToServers = new UsersToServers(server, user, role);
        Session session =  sessionFactory.openSession();
        session.beginTransaction();
        session.persist(usersToServers);
        session.getTransaction().commit();
        session.close();
    }
    
    @Override
    public List<Server> listUserServers(long id) {
        List<Server> servers = new ArrayList<Server>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from UsersToServers p where p.user.id=:id");
        query.setParameter("id", id);
        servers = query.list();
        session.close();
        return servers;
    }
    
    public void addUserToServer(Server server, User user, ServerRole role){
        System.out.println(user + " " + server + " " + role);
        Session session =  sessionFactory.openSession();
        UsersToServers created = new UsersToServers(server,user,role);
        session.beginTransaction();
        session.persist(created);
        session.getTransaction().commit();
    }
    
    public boolean removeUserToServer(Server server, User user){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete UsersToServers p where user.id = :id and server.id = :SERVERID");
        query.setParameter("id",user.getId());
        query.setParameter("SERVERID",server.getId());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result > 0;
    }
    
    public List<ServerRole> listServerRoles(){
        List<ServerRole> roles = new ArrayList<ServerRole>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from ServerRole p");
        roles = query.list();
        session.close();
        return roles;
    }
    
    public ServerRole getDefaultServerRole(){
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from ServerRole p where p.name =:NAME");
        query.setParameter("NAME","user");
        ServerRole role = (ServerRole)query.uniqueResult();
        session.close();
        return role;
    }
    
    public List<ServerType> listTypes(){
        List<ServerType> types = new ArrayList<ServerType>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from ServerType p");
        types = query.list();
        session.close();
        return types;
    }
    
    public List<Servers> listNotUsersServers(long id){
        List<Servers> objects = new ArrayList<Servers>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from Server p where deleted = 0 and p not in (select q.server from"
                + " UsersToServers q where q.user.id =:ID)");
        query.setParameter("ID", id);
        objects = query.list();
        session.close();
        return objects;
    }
    
    public List<UsersToServers> listServersWithRole(String role, long id) {
        List<UsersToServers> servers = new ArrayList<UsersToServers>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from UsersToServers p where p.serverRole.name=:NAME and p.server.id=:ID");
        query.setParameter("NAME", role);
        query.setParameter("ID", id);
        servers = query.list(); 
        session.close();
        return servers;
    }
    
    public List<Server> listOutdated(Date date1) {
        List<Server> servers = new ArrayList<>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from Server p where deleted = 0 and p.dueDate < :date1").setDate("date1", date1);
        servers = query.list();
        session.close();
        return servers;
    }
}
