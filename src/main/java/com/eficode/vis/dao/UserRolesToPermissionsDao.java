package com.eficode.vis.dao;

import com.eficode.vis.model.UserRoleToPermissions;
import com.eficode.vis.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserRolesToPermissionsDao {

    protected static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    protected static final String className = "UserRoleToPermissions";
    
    public Boolean userPermissionsCheck(long userId, String methodName) {
        UserRoleToPermissions rolesToPermissions = new UserRoleToPermissions();
        Session session  = sessionFactory.openSession();
        Query query = session.createQuery("from UserRoleToPermissions p\n" +
            "where p.userRole.id = (select u.userRole.id from User u where u.id = :USER_ID)\n" +
            "and p.userPermission.id = (select t.id from UserPermission t where t.name = :PERMISSION)");
        query.setParameter("USER_ID", userId);
        query.setParameter("PERMISSION", methodName);
        rolesToPermissions =(UserRoleToPermissions) query.uniqueResult();
        session.close();
        return (rolesToPermissions != null);
    }
    
}
