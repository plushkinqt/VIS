package com.eficode.vis.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 *
 * @author yevgen
 */
public class ServersTest {
    
    private static int version;
    private static long userId;
    private static String password;
    private static String urlBase;
    
    public ServersTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ServersTest.version = 2;
        ServersTest.userId = 2l;
        ServersTest.password = "SuperUser1Pass";
        ServersTest.urlBase = "http://localhost:8080/VIS/servers";
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   /*
    @Test
    public void testListServers() {
        System.out.println("listServers");
        String result = doGetRequest("/");
        System.out.println("Result: " + result);
        assertThat(result, containsString(getResponceHeader("Servers", "listServers")));
    }*/
    
    public String doGetRequest(String subUrl) {
        HttpURLConnection con = this.createConnection(subUrl);
        return getRequestData(con);
    }
    
    private HttpURLConnection createConnection(String subUrl) {
        HttpURLConnection con = null;
        
        try {
            URL obj = new URL(ServersTest.urlBase + subUrl);
            con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("version", Integer.toString(ServersTest.version));
            con.setRequestProperty("userId", Long.toString(ServersTest.userId));
            con.setRequestProperty("password", ServersTest.password);
            con.setRequestProperty("message", "");
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServersTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(ServersTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServersTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
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
            Logger.getLogger(ServersTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response.toString();
    }
    
    private String getResponceHeader(String service, String action) {
        return "{\"header\":{\"version\":" + Integer.toString(ServersTest.version) + ",\"service\":\"" + service + "\",\"action\":\"" + action + "\",\"result\":true,\"message\":\"\"}";
    }
    
}
