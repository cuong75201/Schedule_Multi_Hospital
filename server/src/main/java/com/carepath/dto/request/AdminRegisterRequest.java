package com.carepath.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegisterRequest {
    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100, message = "Họ tên tối đa 100 ký tự")
    private String fullName;

    @Email(message = "Email không hợp lệ")
    @Size(max = 254, message = "Email tối đa 254 ký tự")
    private String email;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Pattern (regexp = "[a-zA-Z0-9._-]{3,50}", message = "Tên đăng nhập phải có 3–50 ký tự: chữ, số, dấu chấm, gạch dưới hoặc gạch ngang")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 72, message = "Mật khẩu phải có từ 6 đến 72 ký tự")
    private String password;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu")
    @Size(max = 72, message = "Mật khẩu xác nhận tối đa 72 ký tự")
    private String confirmPassword;

}
