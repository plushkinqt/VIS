package com.eficode.vis.dao;

import static com.eficode.vis.dao.UserRolesToPermissionsDao.sessionFactory;
import com.eficode.vis.model.ServerRolesToServerPermissions;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class ServerRoleToServerPermissionDao {

    public Boolean serverPermissionCheck(long userId, String permission){
        System.out.println("Permission: " + permission);
        
        ServerRolesToServerPermissions rolesToPermissions = new ServerRolesToServerPermissions();
        Session session  = sessionFactory.openSession();
        Query query2 = session.createQuery("from ServerRolesToServerPermissions lo");
        List<ServerRolesToServerPermissions> list= query2.list();
        for(ServerRolesToServerPermissions s:list){
            System.out.println(s);
        }
        Query query = session.createQuery("select u from ServerPermission p, ServerRolesToServerPermissions u, ServerRole r, UsersToServers s\n" +
        "where p.name = :PERMISSION and u.serverPermission.id = p.id\n" + 
        "and u.serverRole.id = r.id and r.id = s.serverRole.id and s.user.id = :ID");
        query.setParameter("PERMISSION", permission);
        query.setParameter("ID", userId);
        rolesToPermissions = (ServerRolesToServerPermissions) query.uniqueResult();
        session.close();
        if(rolesToPermissions == null)
            return false;
        return (list.contains(rolesToPermissions));
        
    }
}
