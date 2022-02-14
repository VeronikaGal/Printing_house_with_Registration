package com.galeeva.project.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    private Long id;
    private String name;
    private String phoneNumber;
    private String address;
    private Role role;
    private String email;
    private String password;


}

