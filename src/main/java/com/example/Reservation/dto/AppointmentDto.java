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

    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@" + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$", message = "Email must be like this  'username@domain.com'"  )
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Pattern(regexp = "[0-9]{11}" , message = "Phone Number must be 11 number and must be numbers" )
    @NotBlank(message = "Phone number is mandatory")
    private String phone;


    @NotNull(message = "you have to choose service type")
    private ServiceType serviceType;
}
