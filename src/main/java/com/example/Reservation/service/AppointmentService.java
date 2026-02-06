package com.example.Reservation.service;

import com.example.Reservation.dto.AppointmentDto;
import com.example.Reservation.entity.Appointment;
import com.example.Reservation.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;


import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private static final ZoneId EGYPT_ZONE = ZoneId.of("Africa/Cairo");
    private static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    private static final Long SPECIALIST_ID = 1L;

//   public List<ZonedDateTime> getAvailableSlots(LocalDate fromDate, int daysAhead) {
//        // Defaults لو الباراميترات مش مرسلة
//        if (fromDate == null) {
//            fromDate = LocalDate.now(EGYPT_ZONE);
//        }
//        if (daysAhead < 0) {
//            daysAhead = 7;
//        }
//
//        ZonedDateTime nowEgypt = ZonedDateTime.now(EGYPT_ZONE);
//        LocalDate today = nowEgypt.toLocalDate();
//
//        // لا نعرض مواعيد من الماضي
//        if (fromDate.isBefore(today)) {
//            fromDate = today;
//        }
//
//        List<ZonedDateTime> possibleSlots = new ArrayList<>();
//        LocalDate current = fromDate;
//
//        for (int i = 0; i <= daysAhead; i++) {
//            DayOfWeek dow = current.getDayOfWeek();
//            if (dow == DayOfWeek.FRIDAY || dow == DayOfWeek.SATURDAY) {
//                current = current.plusDays(1);
//                continue;
//            }
//
//            for (int hour = 18; hour < 22; hour++) {
//                LocalTime slotTime = LocalTime.of(hour, 0);
//                // بناء السلوت بتوقيت مصر
//                ZonedDateTime slotEgypt = ZonedDateTime.of(current, slotTime, EGYPT_ZONE);
//
//                // فقط السلوتات اللي لسة جاية
//                if (slotEgypt.isAfter(nowEgypt)) {
//                    // تحويل إلى UTC للإرجاع والمقارنة مع المحجوز
//                    ZonedDateTime slotUtc = slotEgypt.withZoneSameInstant(UTC_ZONE);
//                    possibleSlots.add(slotUtc);
//                }
//            }
//            current = current.plusDays(1);
//        }
//
//        // جيب المواعيد المحجوزة في النطاق
//        // تحويل fromDate و end إلى ZonedDateTime بنفس التوقيت
//        ZonedDateTime rangeStart = fromDate.atStartOfDay(EGYPT_ZONE)
//                .withZoneSameInstant(UTC_ZONE);
//        ZonedDateTime rangeEnd = fromDate.plusDays(daysAhead)
//                .atTime(23, 59, 59)
//                .atZone(EGYPT_ZONE)
//                .withZoneSameInstant(UTC_ZONE);
//
//        List<Appointment> booked = appointmentRepository.findByBookedTrueAndDateTimeBetween(rangeStart, rangeEnd);
//
//        Set<ZonedDateTime> bookedTimes = booked.stream()
//                .map(Appointment::getDateTime)      // ZonedDateTime
//                .map(zdt -> zdt.withZoneSameInstant(UTC_ZONE))   // تأكيد أنه UTC
//                .collect(Collectors.toSet());
//
//        // إرجاع السلوتات المتاحة
//        return possibleSlots.stream()
//                .filter(slot -> !bookedTimes.contains(slot))
//                .sorted()
//                .collect(Collectors.toList());
//    }
    public List<ZonedDateTime> getAvailableSlots(LocalDate fromDate, int daysAhead) {

        if (fromDate == null) {
            fromDate = LocalDate.now(EGYPT_ZONE);
        }
        if (daysAhead < 0) {
            daysAhead = 7;
        }

        ZonedDateTime nowEgypt = ZonedDateTime.now(EGYPT_ZONE);
        LocalDate today = nowEgypt.toLocalDate();

        // لا نعرض مواعيد من الماضي
        if (fromDate.isBefore(today)) {
            fromDate = today;
        }
        // ... الجزء الأول نفسه (fromDate defaults, nowEgypt, ...)

        List<Instant> possibleSlots = new ArrayList<>();
        LocalDate current = fromDate;

        for (int i = 0; i <= daysAhead; i++) {
            DayOfWeek dow = current.getDayOfWeek();
            if (dow == DayOfWeek.FRIDAY || dow == DayOfWeek.SATURDAY) {
                current = current.plusDays(1);
                continue;
            }

            for (int hour = 18; hour <= 22; hour++) {
                ZonedDateTime slotEgypt = ZonedDateTime.of(current, LocalTime.of(hour, 0), EGYPT_ZONE);
                if (slotEgypt.isAfter(nowEgypt)) {
                    possibleSlots.add(slotEgypt.toInstant());
                }
            }
            current = current.plusDays(1);
        }

        ZonedDateTime startUtc = fromDate.atStartOfDay(EGYPT_ZONE).withZoneSameInstant(UTC_ZONE);
        ZonedDateTime endUtc = fromDate.plusDays(daysAhead).atTime(23, 59, 59)
                .atZone(EGYPT_ZONE).withZoneSameInstant(UTC_ZONE);

        List<Appointment> booked = appointmentRepository.findByBookedTrueAndDateTimeBetween(startUtc, endUtc);

        Set<Instant> bookedTimes = booked.stream()
                .map(a -> a.getDateTime().toInstant())
                .collect(Collectors.toSet());
        return possibleSlots.stream()
                .filter(slot -> !bookedTimes.contains(slot))
                .sorted()
                .map(instant -> instant.atZone(ZoneOffset.UTC))   // أو instant.atOffset(ZoneOffset.UTC)
                .collect(Collectors.toList());
    }

    // في الـ bookSlot، حوّل إلى UTC قبل الحفظ
    // ... (الكود اللي قبل كده، مع تعديل dateTime إلى ZonedDateTime في الـ entity إذا لزم)

    @Transactional
    public ResponseEntity<?> bookSlot(AppointmentDto request) {
        ZonedDateTime slot = request.getDateTime();  // يجب يكون في EET

        // validation: تحقق duplicate
        appointmentRepository.findByDateTime(slot).ifPresent(app -> {
            if (app.isBooked()) {
                throw new RuntimeException("this appointment is already booked try another one");
            }
        });

        if (slot.isBefore(ZonedDateTime.now(EGYPT_ZONE))) {
            throw new RuntimeException("you can't book appointment in the past");
        }

        Appointment newApp = new Appointment();
        newApp.setDateTime(slot);
        newApp.setClientName(request.getClientName());
        newApp.setServiceType(request.getServiceType());
        newApp.setBooked(true);
         appointmentRepository.save(newApp);
        return ResponseEntity.ok("reserved sucessfuly");
    }

    // ... باقي الmethods زي عرض للأدمن أو المتخصص

//    public List<Appointment> getBookedAppointmentsForDay(LocalDate day) {
//        ZonedDateTime startOfDay = day.atStartOfDay(EGYPT_ZONE);
//        ZonedDateTime endOfDay = day.atTime(23, 59, 59).atZone(EGYPT_ZONE);
//
//        // جيب كل المحجوزات في اليوم ده
//        return appointmentRepository.findBookedByDate(startOfDay, endOfDay);
//    }
    public List<Appointment> getBookedAppointmentsForDay(LocalDate day) {
        // نطاق اليوم بتوقيت مصر (القاعدة الثابتة)
        ZonedDateTime startOfDayEgypt = day.atStartOfDay(EGYPT_ZONE);
        ZonedDateTime endOfDayEgypt = day.atTime(23, 59, 59).atZone(EGYPT_ZONE);

        // حوّل لـ UTC عشان الداتابيز
        ZonedDateTime startUtc = startOfDayEgypt.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUtc = endOfDayEgypt.withZoneSameInstant(ZoneId.of("UTC"));

        return appointmentRepository.findBookedByDate(startUtc, endUtc);
    }
}






