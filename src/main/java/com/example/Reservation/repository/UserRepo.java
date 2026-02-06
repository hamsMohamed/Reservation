package com.example.Reservation.repository;

import com.example.Reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    List<User>findByRolesName(String name);


}
