package com.sparta.springBlog.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "최소 4, 최대 8글자이며 알파벳 소문자와 숫자로 구성되어야 합니다.")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z0-9])(?=.*[`~!@#$%^&*()-_+={\\[}\\]:;\"'<>,./?|\\\\])[A-Za-z0-9`~!@#$%^&*()-_+={\\[}\\]:;\"'<>,./?|\\\\]{8,15}", message = "최소 8, 최대 15글자이며 특수문자를 포함한 알파벳 대소문자와 숫자와 구성되어야 합니다.")
    private String password;

    private boolean admin = false;
}
