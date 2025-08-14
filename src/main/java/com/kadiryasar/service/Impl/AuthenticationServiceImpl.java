package com.kadiryasar.service.Impl;

import com.kadiryasar.dto.*;
import com.kadiryasar.exception.BaseException;
import com.kadiryasar.exception.ErrorMessage;
import com.kadiryasar.exception.MessageType;
import com.kadiryasar.jwt.JWTService;
import com.kadiryasar.model.RefreshToken;
import com.kadiryasar.model.User;
import com.kadiryasar.repository.RefreshTokenRepository;
import com.kadiryasar.repository.UserRepository;
import com.kadiryasar.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JWTService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;



    private User createUser(AuthRegisterRequest input){
        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRoleType(input.getRoleType());

        return user;
    }

    private RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000*60*60*4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);

        return refreshToken;
    }

    @Override
    public DtoUser register(AuthRegisterRequest input) {
        DtoUser dtoUser = new DtoUser();

        User savedUser = userRepository.save(createUser(input));
        BeanUtils.copyProperties(savedUser,dtoUser);
        dtoUser.setRoleType(input.getRoleType());

        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest input) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword());
            authenticationProvider.authenticate(authenticationToken);

            Optional<User> optionalUser = userRepository.findByUsername(input.getUsername());

            if (optionalUser.isPresent()) {
                String accessToken = jwtService.generateToken(optionalUser.get());
                RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optionalUser.get()));

                return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
            }
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND,input.getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID,e.getMessage()));
        }
    }

    public boolean isValidRefreshToken(Date expiredDate){
        return new Date().before(expiredDate);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest input) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(input.getRefreshToken());

        if (optionalRefreshToken.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND,input.getRefreshToken()));
        }if (!isValidRefreshToken(optionalRefreshToken.get().getExpiredDate())){
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED,input.getRefreshToken()));
        }
        User user = optionalRefreshToken.get().getUser();
        String accessToken = jwtService.generateToken(user);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }

}
























