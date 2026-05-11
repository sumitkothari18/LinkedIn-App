package com.example.LinkedIn.user_service.service.impl;

import com.example.LinkedIn.user_service.dto.LoginRequestDto;
import com.example.LinkedIn.user_service.dto.SignupRequestDto;
import com.example.LinkedIn.user_service.dto.UserDto;
import com.example.LinkedIn.user_service.entity.User;
import com.example.LinkedIn.user_service.event.UserCreatedEvent;
import com.example.LinkedIn.user_service.exceptions.BadExceptionError;
import com.example.LinkedIn.user_service.exceptions.ResourceNotFoundException;
import com.example.LinkedIn.user_service.repository.UserRepository;
import com.example.LinkedIn.user_service.service.AuthService;
import com.example.LinkedIn.user_service.service.JwtService;
import com.example.LinkedIn.user_service.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final KafkaTemplate<Long,UserCreatedEvent> userCreatedEventKafkaTemplate;

    @Override
    public UserDto signUp(SignupRequestDto signupRequestDto) throws BadRequestException {

        boolean isExist=userRepository.existsByEmail(signupRequestDto.getEmail());

        if(isExist)
        {
            throw new BadRequestException("User already exists, please login");
        }

        User user = modelMapper.map(signupRequestDto,User.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser=userRepository.save(user);

        UserCreatedEvent userCreatedEvent=UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();

        userCreatedEventKafkaTemplate.send("user_created_topic",userCreatedEvent);

        return modelMapper.map(savedUser,UserDto.class);

    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {

        User user=userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail())
        );

        boolean isPasswordMatch = PasswordUtil.checkPassword(loginRequestDto.getPassword(),user.getPassword());

        if(!isPasswordMatch)
        {
            throw new BadExceptionError("Incorrect Password");
        }

        return jwtService.generateAccessToken(user);

    }


}
