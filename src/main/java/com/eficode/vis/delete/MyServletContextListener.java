package com.eficode.vis.delete;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener{
    
    private DeleteThread backgroundThread = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if ((backgroundThread == null) || (!backgroundThread.isAlive())) {
            backgroundThread = new DeleteThread();
            backgroundThread.setPriority(Thread.MIN_PRIORITY);
            backgroundThread.start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        try {
            backgroundThread.doShutdown();
            backgroundThread.interrupt();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
