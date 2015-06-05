package com.eficode.vis.service;

import com.eficode.vis.dao.UserDao;
import com.eficode.vis.dao.UserRoleDao;
import com.eficode.vis.exception.header.HeaderException;
import com.eficode.vis.exception.header.WrongVersionException;
import com.eficode.vis.exception.validation.ValidationException;
import com.eficode.vis.factory.DaoFactory;
import com.eficode.vis.model.User;
import com.eficode.vis.model.UserRest;
import com.eficode.vis.model.UserRole;
import com.eficode.vis.restorepassword.EmailThread;
import com.eficode.vis.wrapper.IncomingWrapper;
import com.eficode.vis.wrapper.Wrapper;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("users")
public class Users extends AbstractService{
    
    private final UserDao userDao = DaoFactory.getUserDao();
    private Wrapper wrapper = new Wrapper();
    IncomingWrapper incomingWrapper = new IncomingWrapper();
    private final String SERVICE_TYPE = this.getClass().getSimpleName();
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String listUsers(@HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
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
        List<User> list = userDao.list();
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get all users operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
        }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("id") Long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
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
        User user = userDao.get(id);
        if(null == user)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get operation failed");
        else return wrapper.getObjectReplySuccessMessage(user, SERVICE_TYPE, methodName);
    }
    
    @POST
    @Path("/")
    @Consumes("application/json")
    public String createUser(UserRest data, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
            @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.CheckUnauthorizesMessage(version, message);
        }
        catch (WrongVersionException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        UserRole role = (new UserRoleDao()).get("user");
        User user;
        try{
            user = new User(0l,data.getUsername(), data.getPassword(), data.getFirstName(), data.getSecondName(),
                data.getEmail(), data.getPhone(), data.getCompany(), data.getDescription(), role, new Date(), false);
        }
        catch(ValidationException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        try{
            userDao.create(user);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Create operation fail.");
        }
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(@PathParam("id") Long id, UserRest data, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
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
        User user = (new UserDao()).get(id);
        if(user == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "user does not exist");
        try{
        user.setFirstName(data.getFirstName());
        user.setSecondName(data.getSecondName());
        user.setEmail(data.getEmail());
        user.setPhone(data.getPhone());
        user.setCompany(data.getCompany());
        user.setDescription(data.getDescription());
        }
        catch(ValidationException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        try{
            userDao.update(user);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Update operation fail."); 
        } 
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @DELETE
    @Path("/{id}")
    public String deleteUser(@PathParam("id") Long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
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
        if (userDao.delete(id))
            return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
        else
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Delete operation fail.");
    }
    
    @POST
    @Path("/login")
    @Consumes("application/json")
    public String login(UserRest data, @HeaderParam("version") int version, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.CheckUnauthorizesMessage(version, message);
        }
        catch (HeaderException e) {
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        User user = new User();
        try{
             user.setUsername(data.getUsername());
             user.setPassword(data.getPassword());
        } catch(ValidationException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        user = userDao.CheckUsernamePassword(user.getUsername(), user.getPassword());
        if (user != null)
            return wrapper.getObjectReplySuccessMessage(user, SERVICE_TYPE, "login");
        else
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Username or password are not found");
    }
    
    @GET
    @Path("/servers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listVMUsers(@PathParam("id") Long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
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
        List<User> list = userDao.listVMUsers(id);
        if(list == null)
            return wrapper.getListReplyReplyErrorMessage(list, SERVICE_TYPE, methodName, "Get list VM users operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @GET
    @Path("/servers/not/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listNotVMUsers(@PathParam("id") Long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,
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
        List<User> list = userDao.listNotVMUsers(id);
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get list not VM users operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
    @PUT
    @Path("/password/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String changePassword(@PathParam("id") Long id, UserRest data, @HeaderParam("version") int version, 
            @HeaderParam("userid") long userId,@HeaderParam("password") String password, @HeaderParam("message") String message) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.HandleMessage(version,userId,password,message);
        }
        catch (HeaderException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!userPermissionCheck(userId, methodName))
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Insufficient rights");
        User user = (new UserDao()).get(id);
        if(user == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "user does not exist");
        try{
            user.setPassword(data.getPassword());
        }
        catch(ValidationException e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        try{
            userDao.update(user);
        }
        catch(Exception e){
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "change password operation fail."); 
        }
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @POST
    @Path("/restore")
    @Consumes("application/json")
    public String restorePassword(String email, @HeaderParam("version") int version, @HeaderParam("message") String message){
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        try {
            incomingWrapper.CheckUnauthorizesMessage(version, message);
        }
        catch (HeaderException e) {
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, e.getMessage());
        }
        if(!User.AbstractValidate("^[a-zA-Z0-9_.+-]+.{3,25}@[a-zA-Z0-9-]+.{2,15}[a-zA-Z0-9-.]+.{2,5}$", email))
            return wrapper.getErrorMessage(SERVICE_TYPE, "restore password", "invalid email address");
        User user = (new UserDao()).getPassword(email);
        if(user == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "user with such email does not exist");
        Thread senderThread = new EmailThread(email, user.getPassword());
        senderThread.start();
        return wrapper.getSuccessMessage(SERVICE_TYPE, methodName);
    }
    
    @PUT
    @Path("/rights/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String changeRights(@PathParam("id") Long id, @HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
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
        User user = (new UserDao()).get(id);
        if(user == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "user does not exist");
        UserRole role = (new UserRoleDao()).get("admin");
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
    @Path("/roles")
    public String listUserRoles(@HeaderParam("version") int version, @HeaderParam("userid") long userId,@HeaderParam("password") String password,
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
        List<UserRole> list = userDao.listUsersRoles();
        if(list == null)
            return wrapper.getErrorMessage(SERVICE_TYPE, methodName, "Get server roles operation failed");
        else return wrapper.getListReplyReplySuccessMessage(list, SERVICE_TYPE, methodName);
    }
    
}