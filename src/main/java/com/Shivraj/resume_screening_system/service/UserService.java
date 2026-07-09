package com.Shivraj.resume_screening_system.service;

import com.Shivraj.resume_screening_system.dto.request.RegisterRequest;
import com.Shivraj.resume_screening_system.dto.response.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

}
