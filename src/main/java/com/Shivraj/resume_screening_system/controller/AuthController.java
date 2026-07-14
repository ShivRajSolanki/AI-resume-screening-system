package com.Shivraj.resume_screening_system.controller;


import com.Shivraj.resume_screening_system.dto.request.RegisterRequest;
import com.Shivraj.resume_screening_system.dto.response.RegisterResponse;
import com.Shivraj.resume_screening_system.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
        public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
            return userService.register(request);
        }
    }


