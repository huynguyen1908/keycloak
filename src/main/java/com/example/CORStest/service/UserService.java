package com.example.CORStest.service;

import com.example.CORStest.dto.Respone.LoginRespone;
import com.example.CORStest.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User register(User user);
    LoginRespone login(String username, String password);
    void logout();
    boolean deleteUser(String id);
    Optional<User> getUser(String id);
}
