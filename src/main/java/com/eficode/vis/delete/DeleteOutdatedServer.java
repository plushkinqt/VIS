package com.eficode.vis.delete;

import com.eficode.vis.dao.ServerDao;
import com.eficode.vis.model.Server;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DeleteOutdatedServer {
    

    void delete() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        Calendar cal = Calendar.getInstance();
        java.util.Date date = cal.getTime();
        System.out.println(date);
        ServerDao serverDao = new ServerDao();
        List<Server> list = serverDao.listOutdated(date);
        if(list != null){
            for(Server s:list){
                serverDao.delete(s.getId());
            }
        }
    }

    
}
