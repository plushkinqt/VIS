package com.eficode.vis.dao;

import com.eficode.vis.model.ServerRole;
import com.eficode.vis.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ServerRoleDao {

    protected static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    protected static final String className = "ServerRole";
    
    public ServerRole get(String name) {
        ServerRole object = null;
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from " + this.className + " p where p.name = :NAME");
        query.setParameter("NAME", name);
        object = (ServerRole) query.uniqueResult();
        session.close();
        return object;
    }
    
}