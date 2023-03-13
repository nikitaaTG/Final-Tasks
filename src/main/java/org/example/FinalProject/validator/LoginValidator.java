package org.example.FinalProject.validator;

import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.models.UserEntity;
import org.example.FinalProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;
        String email = user.getEmail();
        String password = user.getPassword();
        UserDTO userInDB = userService.getUserByEmail(email);
        String passwordInDB = userInDB.getPassword();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required", "123");
        if (userInDB == null) {
            errors.rejectValue("email", "Different.user.email", "Incorrect email or password.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (!passwordInDB.equals(password)) {
            errors.rejectValue("password", "Different.user.password", "Incorrect email or password.");
        }
    }
}