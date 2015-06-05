package com.eficode.vis.service;

import com.eficode.vis.consumer.ConsumerRequests;
import com.eficode.vis.consumer.PasswordModel;
import com.eficode.vis.consumer.ServerModel;
import com.eficode.vis.dao.ServerDao;
import com.eficode.vis.dao.ServerRoleDao;
import com.eficode.vis.dao.ServerStatusDao;
import com.eficode.vis.dao.ServerTypeDao;
import com.eficode.vis.dao.UserDao;
import com.eficode.vis.dao.UserRoleDao;
import com.eficode.vis.exception.header.HeaderException;
import com.eficode.vis.exception.validation.ValidationException;
import com.eficode.vis.factory.DaoFactory;
import com.eficode.vis.model.Server;
import com.eficode.vis.model.ServerRest;
import com.eficode.vis.model.ServerRole;
import com.eficode.vis.model.ServerStatus;
import com.eficode.vis.model.ServerType;
import com.eficode.vis.model.User;
import com.eficode.vis.model.UserRest;
import com.eficode.vis.model.UserRole;
import com.eficode.vis.model.UsersToServers;
import com.eficode.vis.wrapper.IncomingWrapper;
import com.eficode.vis.wrapper.Wrapper;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("servers")
public class Servers extends AbstractService {
    
    private final ServerDao serverDao = DaoFactory.getServerDao();
    private Wrapper wrapper = new Wrapper();
    private IncomingWrapper incomingWrapper = new IncomingWrapper();
    private final String SERVICE_TYPE = this.getClass().getSimpleName();
    private final UserDao userDao = DaoFactory.getUserDao();
    ConsumerRequests cr = new ConsumerRequests();
    
    @GET
    @Path("/")
    public String listServers(@HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        List<Server> list = serverDao.list();
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get all servers operation failed");
        else
            return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/{id}")
    public String getServer(@PathParam("id") long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        Server server = serverDao.get(id);
        if(null == server)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get operation failed");
        else 
            return wrapper.getObjectReplySuccessMessage(server, SERVICE_TYPE, methodName);
    }
    
    @POST
    @Path("/")
    @Consumes("application/json")
    public String createServer(ServerRest data, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) throws ParseException {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        ServerType type = (new ServerTypeDao()).get(data.getServerType());
        ServerStatus status = (new ServerStatusDao()).get("Running");
        System.out.println(status.getName() + status.getDescription());
        Server server;
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 30);
        try{
            server = new Server(0l, data.getName(), type, status, c.getTime(), data.getDescription(), date, false);
        }catch(ValidationException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        User user = userDao.get(userId);
        ServerRole role = (new ServerRoleDao()).get("admin");
        try{
            serverDao.create(server, user, role);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Create operation fail.");
        }
        ServerModel serverModel = new ServerModel(server.getId(), server.getServerType().getVzstring());
        String str = cr.doCreateRequest(serverModel);
        if (str == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Create operation fail.");
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @PUT
    @Path("/{id}")
    public String updateServer(@PathParam("id") long id, ServerRest data, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        Server server = (new ServerDao()).get(id);
        if(server == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "server does not exist");
        try{
            server.setName(data.getName());
            server.setDescription(data.getDescription());
        }catch(ValidationException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        try{
            serverDao.update(server);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Update operation fail.");
        }
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @DELETE
    @Path("/{id}")
    public String deleteServer(@PathParam("id") long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        Boolean result = serverDao.delete(id);
        if (!result)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Delete operation fail.");
        String str = cr.doDeleteRequest(id);
            return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/users/{id}")
    public String listServersByUser(@PathParam("id") long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        List<Server> list = serverDao.listUserServers(id);
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get user servers operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/users")
    public String listServersByCurrentUser(@HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        List<Server> list = serverDao.listUserServers(userId);
        if(list == null)
            return wrapper.getListReplyReplyErrorMessage(list, SERVICE_TYPE, methodName, "List my servers operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @POST
    @Path("/users/add/{id}")
    public String addUserToExistingServer(UserRest data, @PathParam("id") long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        Server server = serverDao.get(id);
        if(null == server)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "such server does not exist");
        User user = userDao.get(data.getId());
        if(user == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "such user does not exist");
        ServerRole role = serverDao.getDefaultServerRole();
        if(role == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "such role does not exist");
        try{
            serverDao.addUserToServer(server, user, role);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Add user to server operation fail.");
        }
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @DELETE
    @Path("/users/delete/{serverId}/{userToDeleteId}")
    public String removeUserFromExistingServer(@PathParam("serverId") long serverId, @PathParam("userToDeleteId") long userToDeleteId,
            @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        Server server = serverDao.get(serverId);
        if(null == server)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "such server does not exist");
        User user = userDao.get(userToDeleteId);
        if(user == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "such user does not exist");
        List<UsersToServers> list = serverDao.listServersWithRole("admin", serverId);
        if(list.size() == 1)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "last admin standing");
        try{
            Boolean result = serverDao.removeUserToServer(server, user);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "remove user from server operation fail.");
        }
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/roles")
    public String listServerRoles(@HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        List<ServerRole> list = serverDao.listServerRoles();
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get server roles operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @PUT
    @Path("/postpone")
    public String postponeDueDate(ServerRest data, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        Server server = (new ServerDao()).get(data.getId());
        if(server == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "server does not exist");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 60);
        if(server.getDueDate().after(data.getDueDate()) || c.getTime().before(data.getDueDate())){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "this date is not allowed");
        }
        server.setDueDate(data.getDueDate());
        try{
            serverDao.update(server);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "postpone due date operation fail.");
        }
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/types")
    public String listServerTypes(@HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        List<ServerType> list = serverDao.listTypes();
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "List server types operation failed");
        else
            return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/users/not/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listNotUsersServers(@PathParam("id") Long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        List<Servers> list = serverDao.listNotUsersServers(id);
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get list not VM users operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @PUT
    @Path("/rights/{serverId}/{userToDeleteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String changeServerRights(@PathParam("serverId") Long serverId, UserRest data, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        User user = (new UserDao()).get(data.getId());
        if(user == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "user does not exist");
        
        UserRole role = (new UserRoleDao()).get(data.getUserRole());
        user.setUserRole(role);
        try{
            userDao.update(user);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Change rights operation fail"); 
        } 
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/users/user/{id}")
    public String listServersWhereUserRoleIsUser(@PathParam("id") long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        List<UsersToServers> list = serverDao.listServersWithRole("user", id);
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get user servers operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/users/admin/{id}")
    public String listServersWhereUserRoleIsAdmin(@PathParam("id") long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        List<UsersToServers> list = serverDao.listServersWithRole("admin",id);
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get user servers operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/stop/{id}")
    public String stopServer(@PathParam("id") long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        ServerStatus status = (new ServerStatusDao()).get("stopped");
        Server server = serverDao.get(id);
        server.setServerStatus(status);
        try{
            serverDao.update(server);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Update operation fail.");
        }
        ConsumerRequests consumer = new ConsumerRequests();
        consumer.doStopServer(id);
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/start/{id}")
    public String startServer(@PathParam("id") long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        ServerStatus status = (new ServerStatusDao()).get("Running");
        Server server = serverDao.get(id);
        server.setServerStatus(status);
        try{
            serverDao.update(server);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Start server operation fail.");
        }
        ConsumerRequests consumer = new ConsumerRequests();
        consumer.doStartServer(id);
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @PUT
    @Path("/rootpassword/{id}")
    public String changeRootPassword(@PathParam("id") long id, String root, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
            @HeaderParam("password") String password, @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName)){
            if(!serverPermissionCheck(userId, methodName))
                return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient server rights");
        }
        long newId = id + 100;
        String response = cr.doResetRootPassword(new PasswordModel(newId,root));
        if(response == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Update operation fail.");
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
}
