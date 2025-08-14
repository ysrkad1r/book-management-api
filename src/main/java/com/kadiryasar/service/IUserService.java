package com.kadiryasar.service;

import com.kadiryasar.dto.DtoUser;
import com.kadiryasar.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    public DtoUser getCurrentUser();

    public DtoUser getUserById(Long id);

    public Page<DtoUser> getAllUsers(Pageable pageable);

    public DtoUser deleteUserById(Long id);

}
