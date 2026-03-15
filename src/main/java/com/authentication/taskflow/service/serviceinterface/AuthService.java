package com.authentication.taskflow.service.serviceinterface;

import com.authentication.taskflow.dto.request.LoginRequestDto;
import com.authentication.taskflow.dto.response.AuthResponseDto;

public interface AuthService {
    AuthResponseDto login(LoginRequestDto request);
}
