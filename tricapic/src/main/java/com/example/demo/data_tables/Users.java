package com.example.demo.data_tables;

import java.sql.Date;
import jakarta.persistence.*;

// =================================
// 
//      Users_Table_Class
// 
// =================================

@Entity
public class Users {
    
    @Id
    @Column(name = "userId" , nullable = false , unique = true)
    private String userId;

    @Column(name = "email" , nullable = true)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "iconImage")
    private String iconImage;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name ="isLook" , nullable = false)
    private boolean isLook;

    @Column(name = "isNotice" , nullable = false)
    private boolean isNotice;

    @Column(name = "profile")
    private String profile;

    //getter,setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean isLook) {
        this.isLook = isLook;
    }

    public boolean isNotice() {
        return isNotice;
    }

    public void setNotice(boolean isNotice) {
        this.isNotice = isNotice;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
