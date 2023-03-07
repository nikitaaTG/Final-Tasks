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
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if (userService.getUserByEmail(user.getEmail())!=null){
            errors.rejectValue("email", "Duplicate.user.email", "Such e-mail already exists.");
        }
    }
}


//        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password", "Required");
//        if (!(userEntity.getConfirmPassword().equals(userEntity.getPassword()))){
//            errors.rejectValue("confirmPassword", "Different.userForm.password");
//        }
