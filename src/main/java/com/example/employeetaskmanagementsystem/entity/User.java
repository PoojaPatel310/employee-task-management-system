package com.example.employeetaskmanagementsystem.entity;



import lombok.*;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "fname", nullable = false, length = 20)
    private String fname;
    @Column(name = "lname", nullable = false, length = 20)
    private String lname;
    @Column(name = "email", nullable = false, length = 20)
    private String email;
    @Column(name = "password", nullable = false, length = 20)
    private  String password;
    @Column(name = "phone", nullable = false, length = 20)
     private String phone;
    @Column(name = "vcode", nullable = false, length = 20)
     private  String vcode;
    @Column(name = "role", nullable = false, length = 20)
     private String role;
    @Column(name = "reg_date", nullable = false)
    @Temporal(TemporalType.DATE)
     private Date regDate;

    @OneToMany(mappedBy = "assignBy", fetch = FetchType.LAZY)
private Set<Task> assignByTask = new HashSet<>();

  @OneToMany(mappedBy = "assignTo", fetch = FetchType.LAZY)
  private Set<Task> assignToTask = new HashSet<>();
}
