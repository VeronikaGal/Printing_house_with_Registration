package com.galeeva.project.validator;

import com.galeeva.project.dto.CreateUserDto;
import com.galeeva.project.entity.Role;

public class CreateUserValidator implements Validator<CreateUserDto> {

    public static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (object.getName() == null) {
            validationResult.add(Error.of("invalid.name", "Name is invalid"));
        }
        if (object.getAddress() == null) {
            validationResult.add(Error.of("invalid.address", "Address is invalid"));
        }
        if (object.getRole() == null || Role.valueOf(object.getRole()) == null) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if (object.getEmail() == null) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if (object.getPassword() == null) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
