package com.acasssta.chooseCats.services;


import com.acasssta.chooseCats.entities.SystemUser;
import com.acasssta.chooseCats.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    boolean save(SystemUser systemUser);
}
