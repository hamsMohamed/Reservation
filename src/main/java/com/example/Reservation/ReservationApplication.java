package com.example.Reservation;

import com.example.Reservation.constant.Role;
import com.example.Reservation.entity.User;
import com.example.Reservation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);

	}

//	@Bean
//	public CommandLineRunner initAdmin(UserRepository userRepo, PasswordEncoder encoder) {
//		return args -> {
//			if (userRepo.findByUserName("EslamDaghash").isEmpty()) {
//				User coach = new User();
//				coach.setUserName("EslamDaghash");
//				coach.setPassword(encoder.encode("E130890Dcoach"));  // غيريها لاحقًا لكلمة سر قوية
//
//				coach.setPhone("0123456789");
//				coach.setEmail("daghash.eslam@gmail.com");
//				coach.setRole(Role.SPECIALIST);
//				userRepo.save(coach);
//			}
//		};
//	}

}
