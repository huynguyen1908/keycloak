package com.example.CORStest.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {
    private String username;
    private String password;
    private String role;
}
