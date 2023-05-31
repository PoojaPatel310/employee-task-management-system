package com.example.employeetaskmanagementsystem.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @Column(name = "detail", nullable = false, length = 255)
    private String detail;

    @Temporal(TemporalType.DATE)
    @Column(name= "assign_date",nullable = false )
    private Date assignDate;
//    @Column(name = "status", nullable = false, length = 50)
//    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status_id", referencedColumnName = "id", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assignBy", referencedColumnName = "id", nullable = false)
    private User assignBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assignTO", referencedColumnName = "id", nullable = false)
    private User assignTo;

}
