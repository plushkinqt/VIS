package com.eficode.vis.model;

import java.util.Date;

public class UserRest {
    
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
    private String company;
    private String description;
    private String userRole;
    private Date createdDate;

    public UserRest() {
        this.id = 0l;
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.secondName = "";
        this.email = "";
        this.phone = "";
        this.company = "";
        this.description = "";
        this.userRole = "";
        this.createdDate = new Date();
    }

    public UserRest(Long id, String username, String password, String firstName, String secondName, String email, String phone,
            String company, String description, String userRole, Date createdDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.description = description;
        this.userRole = userRole;
        this.createdDate = createdDate;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", firstName=" 
                + firstName + ", secondName=" + secondName + ", email=" + email + ", phone=" + phone + ", company=" 
                + company + ", description=" + description + ", createdDate=" + createdDate  + '}';
    }
    
        
    
}
