package com.carepath.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.carepath.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ApiResponse response = new ApiResponse<>();
        response.setStatus_code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setStatus_code(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException exception) {
        log.error("Exception type={}", exception);
        List<Map<String,String>> errors = new ArrayList<>();
        for (var fieldError : exception.getBindingResult().getFieldErrors()){
            Map<String,String> error = new  LinkedHashMap<>();
            error.put("field",fieldError.getField());
            error.put("message",fieldError.getDefaultMessage());
            errors.add(error);
        }
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        return ResponseEntity.badRequest().body(ApiResponse.<List<Map<String, String>>>builder().status_code(errorCode.getCode()).message(errorCode.getMessage()).result(errors).build());
    }

}
