package com.example.Reservation.controller;

import com.example.Reservation.entity.Appointment;
import com.example.Reservation.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/specialist")
public class SpecialistController {

    private final AppointmentService service;

    @GetMapping("/booked/{day}")
    public List<Appointment> getBookedForDay(@PathVariable
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return service.getBookedAppointmentsForDay(day);
    }

}
