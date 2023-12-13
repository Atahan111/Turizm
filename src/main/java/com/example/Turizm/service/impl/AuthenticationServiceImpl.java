package com.example.Turizm.service.impl;

import com.example.Turizm.entity.User;
import com.example.Turizm.enums.Role;
import com.example.Turizm.exception.BadRequestException;
import com.example.Turizm.model.JwtAuthenticationResponse;
import com.example.Turizm.model.SignInRequest;
import com.example.Turizm.model.SignUpRequest;
import com.example.Turizm.repository.UserRepository;
import com.example.Turizm.service.AuthenticationService;
import com.example.Turizm.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        var user = userRepository.findByUsername(request.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("Invalid login or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    @Override
    public void createManager(SignUpRequest request) {
        if (userRepository.findByUsername(request.getLogin()).isEmpty()) {
            User user = User.builder()
                    .username(request.getLogin())
                    .fio(request.getFio())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.MANAGER)
                    .numberPhone(request.getNumberPhone())
                    .build();
            user = userRepository.save(user);
        } else {
            throw new BadRequestException("Manager с таким login уже существует!!!");
        }
    }

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {

        if (userRepository.findByUsername(request.getLogin()).isEmpty()) {
            User user = User.builder()
                    .username(request.getLogin())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .numberPhone(request.getNumberPhone())
                    .build();
            user = userRepository.save(user);
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        } else {
            throw new BadRequestException("Пользотвель с таким username уже существует!!!");
        }

    }
}
