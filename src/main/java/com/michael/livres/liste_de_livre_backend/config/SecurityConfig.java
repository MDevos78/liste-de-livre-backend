package com.michael.livres.liste_de_livre_backend.config;

import com.michael.livres.liste_de_livre_backend.filter.JwtAuthFilter;
import com.michael.livres.liste_de_livre_backend.repository.UserRepository;
import com.michael.livres.liste_de_livre_backend.service.UserInfoUserDetailsService;
import com.michael.livres.liste_de_livre_backend.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

 @Bean
 public UserDetailsService userDetailsService(UserRepository userRepository) {
     return new UserInfoUserDetailsService(userRepository);
 }
 
 @Bean
 public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
 }
 
 // Le filtre JWT est maintenant un bean et prend ses dépendances en paramètres
 @Bean
 public JwtAuthFilter jwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
     return new JwtAuthFilter(jwtService, userDetailsService);
 }
 
 // Le fournisseur d'authentification prend ses dépendances en paramètres
 @Bean
 public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
     DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
     authenticationProvider.setUserDetailsService(userDetailsService);
     authenticationProvider.setPasswordEncoder(passwordEncoder);
     return authenticationProvider;
 }
 
 @Bean
 public CorsConfigurationSource corsConfigurationSource() {
     CorsConfiguration configuration = new CorsConfiguration();
     configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "https://liste-de-livre-frontend.vercel.app/"));
     configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
     configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
     configuration.setAllowCredentials(true);
     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     source.registerCorsConfiguration("/**", configuration);
     return source;
 }

 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) throws Exception {
     return http.csrf(csrf -> csrf.disable())
             .cors(cors -> cors.configurationSource(corsConfigurationSource()))
             .authorizeHttpRequests(auth -> auth
                     .requestMatchers("/api/auth/**").permitAll()
                     .requestMatchers("/api/livres/**").authenticated())
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .authenticationProvider(authenticationProvider)
             .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
             .build();
 }
}