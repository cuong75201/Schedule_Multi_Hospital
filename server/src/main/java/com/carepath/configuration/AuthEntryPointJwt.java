package com.carepath.configuration;

import java.io.IOException;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.carepath.dto.response.ApiResponse;
import com.carepath.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class AuthEntryPointJwt  implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                ErrorCode errorCode = ErrorCode.UNAUTHENTICATE;
                
                response.setStatus(errorCode.getStatusCode().value());
                response.setContentType(org.springframework.http.MediaType.APPLICATION_JSONL_VALUE);

                ApiResponse<?> res = ApiResponse.builder().status_code(errorCode.getCode()).message(errorCode.getMessage()).build();

                ObjectMapper objectMapper = new ObjectMapper();

                response.getWriter().write(objectMapper.writeValueAsString(res));
                response.flushBuffer();

                
    }
    
}
