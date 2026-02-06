package com.example.Reservation.entity;

import com.example.Reservation.constant.Role;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String userName;
//    @Enumerated(EnumType.STRING)
//    private Role role ;
    private String email;
    private String password;
    private String phone;



    private String fullName;
    @ManyToMany(fetch = FetchType.EAGER)
//    @JsonIgnore
    @JoinTable(name = "sec_user_roles",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OrderColumn(name = "id")
    private List<RoleModel> roles;

    private boolean isEnabled;

    private boolean isCredentialsNonExpired;

    private boolean isAccountNonLocked;

    private boolean isAccountNonExpired;

    public User(Long userId) {
        super();
        this.id=userId;
    }
}
