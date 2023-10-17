package com.example.demo.data_tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


// =================================
// 
//      Likes_Table_Class
// 
// =================================

@Entity
public class Likes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "likeId" , nullable = false , unique = true)
    private Long likeId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "capsuleId" , nullable = false)
    private Capsules capsules;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId" , nullable = false)
    private Users users;

    //getter,setter
    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Capsules getCapsules() {
        return capsules;
    }

    public void setCapsules(Capsules capsules) {
        this.capsules = capsules;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
