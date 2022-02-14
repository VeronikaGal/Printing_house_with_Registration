package com.galeeva.project.mapper;

import com.galeeva.project.dto.CreateUserDto;
import com.galeeva.project.entity.Role;
import com.galeeva.project.entity.UserEntity;

public class CreateUserMapper implements Mapper<CreateUserDto, UserEntity> {

    public static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public UserEntity mapFrom(CreateUserDto object) {
        return UserEntity.builder()
                .name(object.getName())
                .phoneNumber(object.getPhoneNumber())
                .address(object.getAddress())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(Role.valueOf(object.getRole()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
