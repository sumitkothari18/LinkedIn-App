package com.example.LinkedIn.user_service.controller;

import com.example.LinkedIn.user_service.dto.LoginRequestDto;
import com.example.LinkedIn.user_service.dto.SignupRequestDto;
import com.example.LinkedIn.user_service.dto.UserDto;
import com.example.LinkedIn.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) throws BadRequestException {
        UserDto userDto=authService.signUp(signupRequestDto);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")

    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto)
    {
        String token=authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }
}
