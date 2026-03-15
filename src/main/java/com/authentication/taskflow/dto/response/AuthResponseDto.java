package com.authentication.taskflow.dto.response;

// import statements -
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token; // JWT token for authenticated user
}
