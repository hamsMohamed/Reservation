//package com.example.Reservation.config;
//
//import com.example.Reservation.entity.User;
//import com.example.Reservation.repository.UserRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/specialist/**").hasRole("SPECIALIST")
//                        .requestMatchers("/api/book", "/api/available", "/").permitAll()  // الحجز مفتوح
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/specialist/login")      // صفحة تسجيل دخول مخصصة
//                        .loginProcessingUrl("/specialist/login")
//                        .defaultSuccessUrl("/specialist/dashboard", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/specialist/logout")
//                        .logoutSuccessUrl("/")
//                        .permitAll()
//                )
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepo) {
//        return username -> {
//            User user = userRepo.findByUserName(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//            return User.withUsername(user.getUsername())
//                    .password(user.getPassword())
//                    .roles(user.getRole().name())
//                    .build();
//        };
//    }
//}
//
