package com.example.CORStest.service.impl;

import com.example.CORStest.dto.Respone.LoginRespone;
import com.example.CORStest.entity.User;
import com.example.CORStest.repository.UserRepository;
import com.example.CORStest.service.UserService;
import com.example.CORStest.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
//    @Override
//    public User login(String username, String password){
//        Optional<User> user = userRepository.findByUsername(username);
//        if(user.getUsername() != null && passwordEncoder.matches(password, user.getPassword())) {
//            return user;
//        }
//        return null;
//    }

    @Override
    public LoginRespone login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String jwtToken = jwtUtil.generateToken(user.getUsername());

                return new LoginRespone(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole(),
                        jwtToken
                );
            }
        }
        return null;
    }

    @Override
    public void logout(){}


    @Override
    public boolean deleteUser(String id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public Optional<User> getUser(String id){
        Optional<User> user = userRepository.findById(id);
        return user;
    }
}
