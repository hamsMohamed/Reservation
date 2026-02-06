package com.example.Reservation.entity;

//import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    private String name;
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JsonIgnore
//    @JoinTable(name = "sec_user_roles",joinColumns = @JoinColumn(name = "role_id"),inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"))
//    private List<User> users=new ArrayList<>();
}
