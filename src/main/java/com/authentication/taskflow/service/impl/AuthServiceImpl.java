package com.authentication.taskflow.service.impl;

import org.springframework.stereotype.Service;

import com.authentication.taskflow.dto.request.LoginRequestDto;
import com.authentication.taskflow.dto.response.AuthResponseDto;
import com.authentication.taskflow.dto.response.BaseApiResponse;
import com.authentication.taskflow.dto.response.UserResponseDto;
import com.authentication.taskflow.feign.TaskflowUserClient;
import com.authentication.taskflow.security.JwtService;
import com.authentication.taskflow.service.serviceinterface.AuthService;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final TaskflowUserClient userClient;
    private final JwtService jwtService;
  
    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        log.info("*** [AUTH SERVICE] :: [AuthServiceImpl] :: [login] :: Login attempt for email: {}",
                request.getEmail());

        BaseApiResponse<UserResponseDto> response = userClient.validateCredentials(request);

        if (!response.isSuccess()) {

            log.warn("*** [AUTH SERVICE] :: [AuthServiceImpl] :: [login] :: Login failed for email: {}",
                    request.getEmail());

            throw new RuntimeException("Invalid credentials");
        }

        UserResponseDto user = response.getData();

        String token = jwtService.generateAccessToken(user.getEmail(), user.getRoleName());

        log.info("*** [AUTH SERVICE] :: [AuthServiceImpl] :: [login] :: Login successful | email: {} | role: {}",
                user.getEmail(),
                user.getRoleName());

        return new AuthResponseDto(token);
    }

}
