package com.eficode.vis.delete;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteThread extends Thread{
    
    private DeleteOutdatedServer deleteOutdate;

    public DeleteThread() {
        this.deleteOutdate = new DeleteOutdatedServer();
    }
    
    @Override
    public void run(){
        while(true){
            try {
                deleteOutdate.delete();
            } catch (Exception e) {
                Logger.getLogger(DeleteThread.class.getName()).log(Level.SEVERE, null, e);
            }
            try {
                Thread.sleep(86400000);//24hours
            } catch (InterruptedException ex) {
                Logger.getLogger(DeleteThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    void doShutdown() {
        deleteOutdate.delete();
    }
}
