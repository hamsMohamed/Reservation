package com.example.Reservation.repository;

import com.example.Reservation.entity.Appointment;
import com.example.Reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    @Query(value = "select u from User u where u.userName= :userName")
    Optional<User> findByUserName(@Param("userName") String username);


}
