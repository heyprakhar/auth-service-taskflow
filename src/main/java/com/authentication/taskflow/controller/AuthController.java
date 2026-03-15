package com.authentication.taskflow.controller;

// import statements - 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import com.authentication.taskflow.service.serviceinterface.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.authentication.taskflow.dto.request.LoginRequestDto;
import com.authentication.taskflow.dto.response.AuthResponseDto;
import com.authentication.taskflow.dto.response.BaseApiResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    // login endpoint to authenticate user and return JWT token - 
    @PostMapping("/login")
    public BaseApiResponse<AuthResponseDto> login(@RequestBody LoginRequestDto request) {
        AuthResponseDto token = authService.login(request);
        return new BaseApiResponse<>(true, "Login successful", token);
    }


}
