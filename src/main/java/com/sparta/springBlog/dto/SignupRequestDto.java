package com.sparta.springBlog.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-z0-9]+", message = "알파벳 소문자와 숫자로 구성되어야 합니다.")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "[`~!@#$%^&*()-_+={\\[}\\]:;\"'<>,./?|\\\\]+[A-Za-z0-9]+", message = "특수문자를 포함한 알파벳 대소문자와 숫자와 구성되어야 합니다.")
    private String password;

    private boolean admin = false;
}
