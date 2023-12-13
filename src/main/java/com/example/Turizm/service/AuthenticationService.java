package com.example.Turizm.service;

import com.example.Turizm.model.JwtAuthenticationResponse;
import com.example.Turizm.model.SignInRequest;
import com.example.Turizm.model.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);

    void createManager(SignUpRequest request);
}
