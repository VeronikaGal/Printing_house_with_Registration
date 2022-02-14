package com.galeeva.project.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String name;
    String phoneNumber;
    String address;
    String role;
    String email;
    String password;
}
