package com.example.Reservation.controller;

import com.example.Reservation.dto.AppointmentDto;
import com.example.Reservation.service.AppointmentService;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/available")
    public List<ZonedDateTime> getAvailable(
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(defaultValue = "31") int daysAhead) {

        return appointmentService.getAvailableSlots(fromDate, daysAhead);
    }

    @PostMapping("/book")
    public ResponseEntity<?> book(@Valid @RequestBody AppointmentDto request) {

        return  appointmentService.bookSlot(request);
    }
}
