package com.Shivraj.resume_screening_system.service.impl;

import com.Shivraj.resume_screening_system.dto.request.RegisterRequest;
import com.Shivraj.resume_screening_system.dto.response.RegisterResponse;
import com.Shivraj.resume_screening_system.entity.Enum.Role;
import com.Shivraj.resume_screening_system.entity.User;
import com.Shivraj.resume_screening_system.repository.UserRepository;
import com.Shivraj.resume_screening_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    @Override
    public RegisterResponse register(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");

        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.HR);
        userRepository.save(user);
        return new RegisterResponse("User Registered successfully");
    }



}
