package com.kadiryasar.service;

import com.kadiryasar.dto.*;

public interface IAuthenticationService {

    public DtoUser register(AuthRegisterRequest input);

    public AuthResponse authenticate(AuthRequest input);

    public AuthResponse refreshToken(RefreshTokenRequest input);


}
