package com.example.demo.data_tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


// =================================
// 
//      Photos_Table_Class
// 
// =================================

@Entity
public class Photos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "photoId" , nullable = false , unique = true)
    private Long photoId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "capsuleId" , nullable = false)
    private Capsules capsules;

    @Column(name = "threedData")
    private String threedData;

    @Column(name = "imageData" , nullable = false)
    private String imageData;

    //getter,setter
    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public Capsules getCapsules() {
        return capsules;
    }

    public void setCapsules(Capsules capsules) {
        this.capsules = capsules;
    }

    public String getThreedData() {
        return threedData;
    }

    public void setThreedData(String threedData) {
        this.threedData = threedData;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

}
