package com.example.demo.data_tables;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


// =================================
// 
//      Reports_Table_Class
// 
// =================================

@Entity
public class Reports {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reportId" , nullable = false , unique = true)
    private Long reportId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId" , nullable = false)
    private Users users;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "capsuleId" , nullable = false)
    private Capsules capsules;

    @Column(name = "reportContent" , nullable = false)
    private int reportContent;

    //getter,setter
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Capsules getCapsules() {
        return capsules;
    }

    public void setCapsules(Capsules capsules) {
        this.capsules = capsules;
    }

    public int getReportContent() {
        return reportContent;
    }

    public void setReportContent(int reportContent) {
        this.reportContent = reportContent;
    }

}
