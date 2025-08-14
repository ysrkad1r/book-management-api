package com.kadiryasar.dto;

import com.kadiryasar.enums.RoleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRegisterRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    private RoleType roleType;

}