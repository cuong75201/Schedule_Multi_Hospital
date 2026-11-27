package com.carepath.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor 
@Data 
@Builder 

public class LoginRequest {
    @NotBlank (message="Username không được để trống")
    private String username;
    @NotBlank(message = "Password không được để trống")
    private String password;
}
