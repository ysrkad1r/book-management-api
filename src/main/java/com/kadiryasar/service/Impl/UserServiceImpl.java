package com.kadiryasar.service.Impl;

import com.kadiryasar.dto.DtoUser;
import com.kadiryasar.exception.BaseException;
import com.kadiryasar.exception.ErrorMessage;
import com.kadiryasar.exception.MessageType;
import com.kadiryasar.model.User;
import com.kadiryasar.repository.RefreshTokenRepository;
import com.kadiryasar.repository.UserRepository;
import com.kadiryasar.service.IUserService;
import com.kadiryasar.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SecurityUtil securityUtil;


    public DtoUser toDto(User user){
        DtoUser dtoUser = new DtoUser();
        dtoUser.setCreateTime(user.getCreateTime());
        BeanUtils.copyProperties(user,dtoUser);
        dtoUser.setRoleType(user.getRoleType());

        return dtoUser;
    }

    @Override
    public DtoUser getCurrentUser() {
        String username = securityUtil.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, username)
                ));

        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(user, dtoUser);
        dtoUser.setRoleType(user.getRoleType());

        return dtoUser;
    }


    @Override
    public DtoUser getUserById(Long id) {
        DtoUser dtoUser = new DtoUser();
        User user = userRepository.findById(id).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.THERE_IS_NO_USER,id.toString())));

        BeanUtils.copyProperties(user, dtoUser);
        dtoUser.setRoleType(user.getRoleType());

        return dtoUser;
    }


    @Override
    public Page<DtoUser> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public DtoUser deleteUserById(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(
          MessageType.USER_NOT_FOUND,id.toString()
        )));

        refreshTokenRepository.deleteAllByUserId(userToDelete.getId()); //olusturulmus butun refreshTokenleri siler gecmisteki
        userRepository.deleteById(id); //refreshTokenler silindikten sonra kullaniciyi siler (refreshToken ile user arasinda foreign key oldugu icin once tokenler silinmeli)

        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(userToDelete,dtoUser);
        dtoUser.setRoleType(userToDelete.getRoleType());

        return dtoUser;
    }

}
