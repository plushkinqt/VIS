package com.eficode.vis.cache;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class InitializationListener implements ServletContextListener {    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        CacheManager singletonManager = CacheManager.create();
        Cache memoryOnlyCache = new Cache("dbCache", 100, false, true, 86400,86400);
        singletonManager.addCache(memoryOnlyCache);
        memoryOnlyCache = singletonManager.getCache("dbCache");       
        ctx.setAttribute("dbCache", memoryOnlyCache );           
    }

    

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

