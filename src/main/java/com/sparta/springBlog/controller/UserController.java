package com.sparta.springBlog.controller;

import com.sparta.springBlog.dto.*;
import com.sparta.springBlog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : fieldErrors) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            ApiResponseDto apiResponseDto = new ApiResponseDto("회원 가입 실패", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponseDto);
        }

        try {
            ApiResponseDto apiResponseDto = userService.signup(signupRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponseDto);
        } catch (IllegalArgumentException e) {
            ApiResponseDto apiResponseDto = new ApiResponseDto("중복된 아이디입니다.", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponseDto);
        }

    }

    // 로그인 API
    @PostMapping("/user/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res) {
        try {
            ApiResponseDto apiResponseDto = userService.login(loginRequestDto, res);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponseDto);
        } catch (IllegalArgumentException e) {
            ApiResponseDto apiResponseDto = new ApiResponseDto("ID 혹은 비밀번호 오류입니다.", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponseDto);
        }
    }
}
