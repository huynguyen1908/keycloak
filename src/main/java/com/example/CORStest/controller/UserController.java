package com.example.CORStest.controller;

import com.example.CORStest.dto.Request.LoginRequest;
import com.example.CORStest.dto.Request.UpdateUserRequest;
import com.example.CORStest.dto.Respone.LoginRespone;
import com.example.CORStest.entity.User;
import com.example.CORStest.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = userService.register(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginRespone response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        userService.logout();
        return ResponseEntity.ok("Logout successful");
    }


    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        boolean isDeleted = userService.deleteUser(id);
        if(isDeleted){
            return ResponseEntity.ok("Delete successful");
        }
        return ResponseEntity.status(404).body("User not found");
    }
    @PostMapping("/admin/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest updateUserRequest){
        userService.updateUser(id, updateUserRequest);
        return updateUserRequest != null ? (ResponseEntity<User>) ResponseEntity.ok() : ResponseEntity.status(404).body(null);
    }

    @GetMapping("/admin/get-user")
    public Optional<User> getUser(String id){
        return userService.getUser(id);
    }

    @GetMapping("/admin/get-all")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
}
