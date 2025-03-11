package com.example.CORStest.service;

import com.example.CORStest.dto.Request.UpdateUserRequest;
import com.example.CORStest.dto.Respone.LoginRespone;
import com.example.CORStest.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User register(User user);

    LoginRespone login(String username, String password);

    void logout();

    boolean deleteUser(String id);

    User updateUser(String userId, UpdateUserRequest updateUserRequest);

    Optional<User> getUser(String id);

    List<User> getAllUser();
}
