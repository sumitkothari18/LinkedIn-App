package com.example.LinkedIn.user_service.service;

import com.example.LinkedIn.user_service.dto.LoginRequestDto;
import com.example.LinkedIn.user_service.dto.SignupRequestDto;
import com.example.LinkedIn.user_service.dto.UserDto;
import org.apache.coyote.BadRequestException;

public interface AuthService {

    UserDto signUp(SignupRequestDto signupRequestDto) throws BadRequestException;

    String login(LoginRequestDto loginRequestDto);
}
