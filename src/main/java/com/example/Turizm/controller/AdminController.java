package com.example.Turizm.controller;

import com.example.Turizm.model.JwtAuthenticationResponse;
import com.example.Turizm.model.SignInRequest;
import com.example.Turizm.model.SignUpRequest;
import com.example.Turizm.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;

    @PostMapping("/add-manager")
    public void signIn(@RequestBody SignUpRequest signinRequest) {
        authenticationService.createManager(signinRequest);
    }
}
