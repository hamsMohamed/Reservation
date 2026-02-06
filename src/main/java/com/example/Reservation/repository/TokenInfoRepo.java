package com.example.Reservation.repository;

import com.example.Reservation.entity.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenInfoRepo extends JpaRepository<TokenInfo, Long> {
    Optional<TokenInfo> findByRefreshToken (String refreshToken);
    Optional<TokenInfo> findByAccessToken (String accessToken);
}
