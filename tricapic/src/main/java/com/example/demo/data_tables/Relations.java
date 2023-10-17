package com.example.demo.data_tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

// =================================
// 
//      Relation_Table_Class
// 
// =================================

@Entity
public class Relations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "relationId" , nullable = false , unique = true)
    private Long relationId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "followerId" , referencedColumnName = "userId" , nullable = false)
    private Users followerId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "followedId" , referencedColumnName = "userId" , nullable = false)
    private Users followedId;

    //getter,setter
    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Users getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Users followerId) {
        this.followerId = followerId;
    }

    public Users getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Users followedId) {
        this.followedId = followedId;
    }

}
