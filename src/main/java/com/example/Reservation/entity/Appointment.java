package com.example.Reservation.entity;

import com.example.Reservation.constant.ServiceType;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Data
@Entity
@Table(name = "appointment")
public class Appointment  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime dateTime;
    private boolean booked = false;
    private String clientName;

    private ServiceType serviceType;
}
