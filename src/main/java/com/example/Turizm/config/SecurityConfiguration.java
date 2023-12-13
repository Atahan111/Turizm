package com.example.Turizm.config;

import com.example.Turizm.entity.User;
import com.example.Turizm.enums.Role;
import com.example.Turizm.filter.JwtAuthenticationFilter;
import com.example.Turizm.repository.UserRepository;
import com.example.Turizm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers(AntPathRequestMatcher.antMatcher("/auth/*")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("**")).hasRole(Role.ADMIN.name())
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/add-manager")).hasRole(Role.ADMIN.name())
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/tour/**")).hasRole(Role.MANAGER.name())
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/tour/search-tour")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/tour/get-all-tour")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/booking/**")).hasRole(Role.MANAGER.name())
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/booking/reserve")).hasRole(Role.USER.name())
                                .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public void addAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User user = new User();
            user.setRole(Role.ADMIN);
            user.setPassword("$2a$10$PmCsWizisxCuGOmdvLAd/OgEjguft31/mpH/ifMOO9hzGOndMYhcW");
            user.setUsername("admin");
            userRepository.save(user);
        }
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
