package com.kadiryasar.dto;

import com.kadiryasar.enums.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUser extends DtoBase{

    private String username;

    private String password;

    private RoleType roleType;

}
