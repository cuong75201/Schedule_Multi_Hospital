package com.carepath.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginResponse {
     @ToString.Exclude
    private String token;
    private boolean authenticated;

}
