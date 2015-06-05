package com.eficode.vis.model;

import com.eficode.vis.exception.validation.*;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="users")
public class User implements Serializable {
    
    @Id
    @Expose
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Long id;
    
    @Expose
    @Column(name="username", nullable=false, length=15)
    private String username;
    
    @Column(name="password", nullable=false, length=45)
    private String password;
    
    @Expose
    @Column(name="firstName", nullable=true, length=45)
    private String firstName;
    
    @Expose
    @Column(name="secondName", nullable=true, length =45)
    private String secondName;
    
    @Expose
    @Column(name="email", nullable=false, length=45)
    private String email;
    
    @Expose
    @Column(name="phone", nullable=true, length=15)
    private String phone;
    
    @Expose
    @Column(name="company", nullable=true, length=45)
    private String company;
    
    @Expose
    @Column(name="description")
    private String description;
    
    @Expose
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="role_id", nullable=false)
    private UserRole userRole;
    
    @Expose
    @Column(name="created_date", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    
    @Column(name="deleted")
    private Boolean deleted;

    public User() {
        this.id = 0l;
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.secondName = "";
        this.email = "";
        this.phone = "";
        this.company = "";
        this.description = "";
        this.userRole = new UserRole();
        this.createdDate = new Date();
        this.deleted = false;
    }

    public User(Long id, String username, String password, String firstName, String secondName, String email, String phone,
            String company, String description, UserRole userRole, Date createdDate, Boolean deleted) throws ValidationException{
        this.id = id;
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setSecondName(secondName);
        setEmail(email);
        setPhone(phone);
        setCompany(company);
        setDescription(description);
        this.userRole = userRole;
        this.createdDate = createdDate;
        this.deleted = deleted;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public final void setUsername(String username) throws UsernameException {
        if(!AbstractValidate("^[a-zA-Z0-9_-]{3,15}$", username))
            throw new UsernameException();
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) throws PasswordException {
        if(!AbstractValidate("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,45}$",password))
            throw new PasswordException();
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public final void setFirstName(String firstName) throws FirstNameException {
        if(!AbstractValidate("^[a-zA-Z _'-]{3,45}$",firstName))
            throw new FirstNameException();
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public final void setSecondName(String secondName) throws SecondNameException {
        if(!AbstractValidate("^[a-zA-Z _'-]{3,45}$",secondName))
            throw new SecondNameException();
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) throws EmailException {
        if(!AbstractValidate("^[a-zA-Z0-9_.+-]+.{3,25}@[a-zA-Z0-9-]+.{2,15}[a-zA-Z0-9-.]+.{2,5}$",email))
            throw new EmailException();
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) throws PhoneException {
        if(!AbstractValidate("^[0-9+()-]{5,15}$",phone))
            throw new PhoneException();
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public final void setCompany(String company) throws CompanyException {
        if(!AbstractValidate("^[a-zA-Z0-9_ -]{3,45}$",company))
            throw new CompanyException();
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public final void setDescription(String description) throws DescriptionException {
        if(!AbstractValidate("^.{0,1000}$",description))
            throw new DescriptionException();
        this.description = description;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName + ", secondName=" + secondName + ", email=" + email + ", phone=" + phone + ", company=" + company + ", description=" + description + ", createdDate=" + createdDate + ", deleted=" + deleted + '}';
    }
    
    public static boolean AbstractValidate(String parameter, String toCompare){
        Pattern pattern = Pattern.compile(parameter);
        Matcher matcher = pattern.matcher(toCompare);
        if(!matcher.matches())
            return false;
        else return true;
    }
}
