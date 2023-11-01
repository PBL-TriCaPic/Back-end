package com.example.demo.data_tables;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

// =================================
// 
//      Albums_Table_Class
// 
// =================================

public class Albums {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "relationId" , nullable = false , unique = true)
    private Long relationId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId" , nullable = false)
    private Users users;

    @JsonBackReference
    @OneToMany(mappedBy = "albums", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Capsules> capsulesList;

    //getter,setter
    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Capsules> getCapsulesList() {
        return capsulesList;
    }

    public void setCapsulesList(List<Capsules> capsulesList) {
        this.capsulesList = capsulesList;
    }

    
}
