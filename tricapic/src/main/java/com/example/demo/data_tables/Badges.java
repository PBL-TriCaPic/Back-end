package com.example.demo.data_tables;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


// =================================
// 
//      Badges_Table_Class
// 
// =================================

@Entity
public class Badges {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "badgeId" , nullable = false , unique = true)
    private Long badgeId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId" , nullable = false)
    private Users users;

    @Column(name = "prefecture" , nullable = false)
    private String prefecture;

    //getter,setter
    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

}
