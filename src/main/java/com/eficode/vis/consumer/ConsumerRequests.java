package com.eficode.vis.consumer;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

public class ConsumerRequests {

    private final int version = 1;
    private final long userId = 1;
    private final String password = "password";
    private final String urlBase  = "http://192.168.56.101:8080/VIS-VB-1.0-SNAPSHOT/services";
    Gson gson = new Gson();
    
    public String doCreateRequest(ServerModel server) {
        HttpURLConnection con = this.createConnection("/create", server);
        return getRequestData(con);
    }
    
    public String doDeleteRequest(long id){
        HttpURLConnection con = this.createGetConnection(id, "/destroy/");
        return getRequestData(con);
    }
    
    public String doStartServer(long id){
        HttpURLConnection con = this.createGetConnection(id, "/start/");
        return getRequestData(con);
    }
    
    public String doStopServer(long id){
        HttpURLConnection con = this.createGetConnection(id, "/stop/");
        return getRequestData(con);
    }
    
    public String doResetRootPassword(PasswordModel password){
        HttpURLConnection con = this.createConnection("/reset_password", password);
        return getRequestData(con);
    }
    
    private HttpURLConnection createConnection(String subUrl, Object server) {
        HttpURLConnection connection = null;
        try {
            URL obj = new URL(urlBase + subUrl);
            connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("version", Integer.toString(version));
            connection.setRequestProperty("userId", Long.toString(userId));
            connection.setRequestProperty("password", password);
            connection.setRequestProperty("message", "");
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream data = new DataOutputStream (connection.getOutputStream ());
            data.writeBytes (gson.toJson(server));
            data.flush ();
            data.close ();
        } catch (MalformedURLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }
    
    private HttpURLConnection createGetConnection(long id, String subBuild) {
        HttpURLConnection connection = null;
        StringBuffer sb = new StringBuffer();
        sb.append(subBuild);
        long temp = id+100;
        sb.append(temp);
        String subUrl = sb.toString();
        try {
            
            URL obj = new URL(urlBase + subUrl);
            connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("version", Integer.toString(version));
            connection.setRequestProperty("userId", Long.toString(userId));
            connection.setRequestProperty("password", password);
            connection.setRequestProperty("message", "");
            connection.setUseCaches (false);
        } catch (MalformedURLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
     private String getRequestData(HttpURLConnection con) {
        StringBuffer response = new StringBuffer("");
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        
        return response.toString();
    }

    
}
