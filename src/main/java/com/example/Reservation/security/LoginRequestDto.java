package com.example.Reservation.security;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    @NotEmpty
//    @Email
    @JsonProperty("email")
    private String userName;

    @NotEmpty
    private String password;
}
