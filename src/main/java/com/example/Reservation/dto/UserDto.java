package com.example.Reservation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.*;

@Data
public class UserDto {


    @NotBlank(message = "User Name is mandatory")
    @Size(max = 60, message = "User Name's max length allowed is 60 characters")
    private String userName;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 205, message = "Name's max length allowed is 205 characters")
    private String name;

    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@" + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$", message = "Email must be like this  'username@domain.com'"  )
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 3, max = 50, message = "Password's length allowed is between 3 and 50 characters")
    private String password;
    @Pattern(regexp = "[0-9]{11}" , message = "Phone Number must be 11 number and must be numbers"  )
    @NotBlank(message = "Phone number is mandatory")
    private String phone;
}
