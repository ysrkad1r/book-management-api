package com.kadiryasar.controller;

import com.kadiryasar.dto.DtoUser;
import com.kadiryasar.model.RestPageableEntity;

public interface IRestUserController {

    public RootEntity<DtoUser> getCurrentUser();

    public RootEntity<DtoUser> getUserById(Long id);

    public RootEntity<RestPageableEntity<DtoUser>> getAllUsers(int page,int size,String sortField,boolean asc);

    public RootEntity<DtoUser> deleteUserById(Long id);
}
