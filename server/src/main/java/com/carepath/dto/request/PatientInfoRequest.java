package com.carepath.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientInfoRequest {
    
    @PastOrPresent(message = "Ngày sinh không được ở tương lai")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "Nam|Nữ|Khác", message = "Giới tính không hợp lệ")
    private String gender;

    @Pattern(regexp = "(?:0|\\+84)[1-9][0-9]{8,9}", message = "Số điện thoại phải bắt đầu bằng 0 hoặc +84 và theo sau là 9–10 chữ số hợp lệ")
    private String phone;

    @Email(message = "Email không đúng định dạng")
    @Size(min = 1, max = 254, message = "Email phải có từ 1 đến 254 ký tự")
    private String email;
}
