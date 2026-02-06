package com.example.Reservation.security;

import com.example.Reservation.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Setter
@Getter
public class AppUserDetail implements UserDetails {
    private Long id;
    private String fullName;
    private String userName;
    private String email;
    private String password;
    List<GrantedAuthority> authorities;
    private boolean isEnabled;

    private boolean isCredentialsNonExpired;

    private boolean isAccountNonLocked;

    private boolean isAccountNonExpired;

    public AppUserDetail() {
        super();
    }

    public AppUserDetail(User user) {
        super();
        this.id= user.getId();
        this.fullName=user.getFullName();
        this.userName=user.getUserName();
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.authorities = new ArrayList<>();
        this.isEnabled = user.isEnabled();
        this.isCredentialsNonExpired = user.isCredentialsNonExpired();
        this.isAccountNonExpired = user.isAccountNonExpired();
        this.isAccountNonLocked = user.isAccountNonLocked();
        if(!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

//    @Override
//    public String getUsername() {
//        return userName;
//    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
