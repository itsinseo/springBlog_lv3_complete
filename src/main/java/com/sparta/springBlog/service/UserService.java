package com.sparta.springBlog.service;

import com.sparta.springBlog.jwt.JwtUtil;
import com.sparta.springBlog.dto.*;
import com.sparta.springBlog.entity.User;
import com.sparta.springBlog.entity.UserRoleEnum;
import com.sparta.springBlog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ApiResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        // username 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자명입니다.");
        }

        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER; // 기본값은 사용자 권한
        User user = new User(username, password, role);
        userRepository.save(user);

        return new ApiResponseDto("회원가입 성공", HttpStatus.OK.value());
    }

    public ApiResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // username 확인
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("username 오류")
        );

        // password 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("password 오류");
        }

        // JWT 생성
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        res.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return new ApiResponseDto("로그인 성공", HttpStatus.OK.value());
    }
}
