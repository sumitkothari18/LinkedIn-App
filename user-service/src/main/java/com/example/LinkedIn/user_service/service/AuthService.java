package com.example.LinkedIn.user_service.service;

import com.example.LinkedIn.user_service.dto.LoginRequestDto;
import com.example.LinkedIn.user_service.dto.SignupRequestDto;
import com.example.LinkedIn.user_service.dto.UserDto;

public interface AuthService {

    UserDto signUp(SignupRequestDto signupRequestDto);

    String login(LoginRequestDto loginRequestDto);
}
