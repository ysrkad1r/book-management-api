package com.kadiryasar.controller.Impl;

import com.kadiryasar.controller.IRestUserController;
import com.kadiryasar.controller.RestBaseController;
import com.kadiryasar.controller.RootEntity;
import com.kadiryasar.dto.DtoUser;
import com.kadiryasar.service.IUserService;
import com.kadiryasar.model.RestPageableEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rest/api/admin")
public class RestUserControllerImpl extends RestBaseController implements IRestUserController {

    @Autowired
    private IUserService userService;

    @GetMapping(path = "/get-current-user")
    @Override
    public RootEntity<DtoUser> getCurrentUser() {
        return ok(userService.getCurrentUser());
    }

    @GetMapping(path = "/list/{id}")
    @Override
    public RootEntity<DtoUser> getUserById(@PathVariable(name = "id") Long id) {
        return ok(userService.getUserById(id));
    }

    @GetMapping(path = "/get-all-users")
    @Override
    public RootEntity<RestPageableEntity<DtoUser>> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "false") boolean asc) {

        Pageable pageable = toPageable(page,size,sortField,asc);
        Page<DtoUser> userPage = userService.getAllUsers(pageable);

        return ok(toPageableEntity(userPage));
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public RootEntity<DtoUser> deleteUserById(@PathVariable(name = "id") Long id) {
        return ok(userService.deleteUserById(id));
    }

}
