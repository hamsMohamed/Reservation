package com.example.Reservation.dto;


import com.example.Reservation.constant.ServiceType;
import lombok.Data;
import javax.validation.constraints.*;

import java.time.ZonedDateTime;

@Data
public class AppointmentDto {

    @NotNull(message = "date and time is mandatory")
    @Future(message = "time of reservation must to be in future ")
    private ZonedDateTime dateTime;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 60, message = "User Name's max length allowed is 60 characters")
    private String clientName;

    @NotNull(message = "you have to choose service type")
    private ServiceType serviceType;
}
