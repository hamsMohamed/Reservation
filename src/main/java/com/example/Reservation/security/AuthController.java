package com.example.Reservation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<JWTResponseDto> login (@RequestBody LoginRequestDto loginRequest){

        JWTResponseDto jwtResponseDto = authService.login(loginRequest.getUserName(), loginRequest.getPassword());

        return ResponseEntity.ok(jwtResponseDto);
    }
    @PostMapping("/signUp")
    public ResponseEntity<?>signUp(@RequestBody SignUpRequestDto signupRequest){

        JWTResponseDto jwtResponseDto = authService.signUp(signupRequest.getFullName(), signupRequest.getPhone(),signupRequest.getUserName(), signupRequest.getEmail(), signupRequest.getPassword());

        return ResponseEntity.ok(jwtResponseDto);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<AccessTokenDto> refreshAccessToken(@RequestParam String refreshToken) {

        AccessTokenDto dto = authService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(dto);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String refreshToken) {

        authService.logoutUser(refreshToken);

        return ResponseEntity.ok(null);
    }
}
