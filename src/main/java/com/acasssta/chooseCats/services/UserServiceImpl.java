package com.acasssta.chooseCats.services;

import com.acasssta.chooseCats.entities.*;
import com.acasssta.chooseCats.repositories.CatRepository;
import com.acasssta.chooseCats.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private CatRepository catRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCatRepository(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepository.findOneByUserName(username);
    }

    @Override
    @Transactional
    public boolean save(SystemUser systemUser) {
        User user = new User();

        if (findByUserName(systemUser.getUserName()) != null) {
            return false;
        }

        user.setRole("user");
        user.setUserName(systemUser.getUserName());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setUserCat((List<Cat>) catRepository.findAll());
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findOneByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorities);
    }
}
