package com.eficode.vis.dao;

import com.eficode.vis.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractDao<T> {
    
    protected static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private String className = "";
    
    public AbstractDao(String className) {
        this.className = className;
    }
    
    public List<T> list() {
        List<T> objects = new ArrayList<>();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from " + this.className + " p where deleted = 0");
        objects = query.list();
        session.close();
        return objects;
    }
    
    public T get(Long id) {
        T object = null;
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from " + this.className + " p where p.id = :ID and deleted = 0");
        query.setParameter("ID", id);
        object = (T) query.uniqueResult();
        session.close();
        return object;
    }
    
    public void create(T t) {
        Session session =  sessionFactory.openSession();
        session.beginTransaction();
        session.persist(t);
        session.getTransaction().commit();
        session.close();
    }
    
    public void update(T t) {
        Session session  = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(t);
        session.getTransaction().commit();
        session.close(); 
    }
    
    public Boolean delete(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query2 = session.createQuery("delete UsersToServers p where "+ this.className.toLowerCase() +".id = :ID");
        System.out.println(query2);
        query2.setParameter("ID", id);
        System.out.println(query2);
        int result = query2.executeUpdate();
        Query query = session.createQuery("update " + this.className + " p set deleted = 1 where id = :id");
        query.setParameter("id", id);
        result += query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result > 0;
    }
    
}
