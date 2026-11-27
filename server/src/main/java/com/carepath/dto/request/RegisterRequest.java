package com.carepath.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class RegisterRequest {
    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;
    @NotBlank(message="Username không được để trống")
    private String username;
    @NotBlank(message="Password không được để trống")
    @Size(min = 6, max = 20, message = "Password tối thiểu 6 ký tự, tối đa 20 ký tự")
    private String password;
    @NotBlank(message="Vui lòng nhập lại mật khẩu")
    private String confirmPassword;

    @Valid
    private PatientInfoRequest patientInfo;
}
