package com.example.Reservation.repository;

import com.example.Reservation.entity.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel,Long> {
    RoleModel findByName (String name);
}
