package com.ssdi.project.bookExchange.service;


import com.ssdi.project.bookExchange.model.Role;
import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.repository.RoleRepository;
import com.ssdi.project.bookExchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository,
                       @Qualifier("roleRepository") RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
        return user;
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public List<User> findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    public List<User> searchUsers(String query) {
        return userRepository.searchUsers(query);
    }

}
