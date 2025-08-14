package com.kadiryasar.util;

import com.kadiryasar.exception.BaseException;
import com.kadiryasar.exception.ErrorMessage;
import com.kadiryasar.exception.MessageType;
import com.kadiryasar.model.User;
import com.kadiryasar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component //static method gibi olacak
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails){
                return ((UserDetails) principal).getUsername();
            }else {
                return principal.toString();
            }
        }
        throw new BaseException(new ErrorMessage(MessageType.THERE_IS_NO_USER,""));
    }

    public Long getCurrentUserId() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.USER_NOT_FOUND,username)
        ));
        return user.getId();
    }

}
