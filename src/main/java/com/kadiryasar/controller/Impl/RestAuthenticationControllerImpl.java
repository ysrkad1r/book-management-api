package com.kadiryasar.controller.Impl;

import com.kadiryasar.controller.IRestAuthenticationController;
import com.kadiryasar.controller.RestBaseController;
import com.kadiryasar.controller.RootEntity;
import com.kadiryasar.dto.*;
import com.kadiryasar.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping(path = "/register")
    @Override
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRegisterRequest input) {
        return ok(authenticationService.register(input));
    }

    @PostMapping(path = "/authenticate")
    @Override
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest input) {
        return ok(authenticationService.authenticate(input));
    }

    @PostMapping(path = "/refreshToken")
    @Override
    public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest input) {
        return ok(authenticationService.refreshToken(input));
    }

}
