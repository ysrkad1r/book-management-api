package com.kadiryasar.controller;

import com.kadiryasar.dto.*;

public interface IRestAuthenticationController {

    public RootEntity<DtoUser> register(AuthRegisterRequest input);

    public RootEntity<AuthResponse> authenticate(AuthRequest input);

    public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest input);

}
