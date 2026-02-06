package com.example.Reservation.repository;

import com.example.Reservation.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    // للمتاح: جيب المحجوزات في فترة
    List<Appointment> findByBookedTrueAndDateTimeBetween(ZonedDateTime start, ZonedDateTime end);

    // تحقق لو سلوت محجوز
    Optional<Appointment> findByDateTime(ZonedDateTime dateTime);

    // للمتخصص: مواعيد اليوم
    @Query("SELECT a FROM Appointment a WHERE a.booked = true AND a.dateTime BETWEEN :start AND :end")
    List<Appointment> findBookedByDate(ZonedDateTime start, ZonedDateTime end);
}
