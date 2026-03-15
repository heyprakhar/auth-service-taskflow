package com.authentication.taskflow.feign;

// import statements
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.authentication.taskflow.dto.response.BaseApiResponse;
import com.authentication.taskflow.dto.response.UserResponseDto;
import com.authentication.taskflow.dto.request.LoginRequestDto;

@FeignClient(name = "taskk")
public interface TaskflowUserClient {

    @PostMapping("/api/users/validate-credentials")
    BaseApiResponse<UserResponseDto> validateCredentials(
            @RequestBody LoginRequestDto request);
}