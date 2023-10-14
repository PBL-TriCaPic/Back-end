package com.example.demo.data_tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

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
