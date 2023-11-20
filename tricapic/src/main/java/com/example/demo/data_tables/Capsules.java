package com.example.demo.data_tables;

import java.time.LocalDateTime;

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
//      Capsules_Table_Class
// 
// =================================

@Entity
public class Capsules {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "capsuleId" , nullable = false , unique = true)
    private Long capsulesId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId" , nullable = false)
    private Users users;

    @Column(name = "capsuleDate" , nullable = false)
    private LocalDateTime capsuleDate;

    @Column(name = "videoData")
    private String videoData;

    @Column(name = "capsuleLat" , nullable = false)
    private float capsuleLat;

    @Column(name = "capsuleLon" , nullable = false)
    private float capsuleLon;

    @Column(name = "audioData")
    private String audioData;

    @Column(name = "textData")
    private String textData;

    @Column(name = "pinColor")
    private String pinColor;

    @Column(name = "pinForm")
    private String pinForm;

    @Column(name = "password")
    private String password;

    @Column(name = "reopenDate")
    private LocalDateTime reopenDate;

    //getter,setter
    public Long getCapsulesId() {
        return capsulesId;
    }

    public void setCapsulesId(Long capsulesId) {
        this.capsulesId = capsulesId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public LocalDateTime getCapsuleDate() {
        return capsuleDate;
    }

    public void setCapsuleDate(LocalDateTime capsuleDate) {
        this.capsuleDate = capsuleDate;
    }

    public String getVideoData() {
        return videoData;
    }

    public void setVideoData(String videoData) {
        this.videoData = videoData;
    }

    public float getCapsuleLat() {
        return capsuleLat;
    }

    public void setCapsuleLat(float capsuleLat) {
        this.capsuleLat = capsuleLat;
    }

    public float getCapsuleLon() {
        return capsuleLon;
    }

    public void setCapsuleLon(float capsuleLon) {
        this.capsuleLon = capsuleLon;
    }

    public String getAudioData() {
        return audioData;
    }

    public void setAudioData(String audioData) {
        this.audioData = audioData;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getPinColor() {
        return pinColor;
    }

    public void setPinColor(String pinColor) {
        this.pinColor = pinColor;
    }

    public String getPinForm() {
        return pinForm;
    }

    public void setPinForm(String pinForm) {
        this.pinForm = pinForm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getReopenDate() {
        return reopenDate;
    }

    public void setReopenDate(LocalDateTime reopenDate) {
        this.reopenDate = reopenDate;
    }

    

}
