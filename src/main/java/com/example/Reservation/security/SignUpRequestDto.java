package com.example.Reservation.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequestDto {
    private String fullName;
    private String phone;
    private String userName;
    private String email;
    private String password;
}
