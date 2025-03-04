package com.example.CORStest.dto.Respone;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Getter
@Setter
public class LoginRespone {
    private String id;
    private String username;
    private String password;
    private String role;
    private String jwt;
}
