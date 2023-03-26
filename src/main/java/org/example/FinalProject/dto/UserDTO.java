package org.example.FinalProject.dto;

import java.util.Date;
import java.util.Set;

import org.example.FinalProject.enums.RoleOnSite;
import org.example.FinalProject.models.AddressEntity;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;

    @NotNull(message = "Name is empty")
    @NotBlank(message = "Name is empty")
    @Size(min = 2, max = 55, message = "Name must contain from 2 to 55 characters")
    private String name;

    @NotNull(message = "Surname is empty")
    @NotBlank(message = "Surname is empty")
    @Size(min = 2, max = 55, message = "Surname must contain from 2 to 55 characters")
    private String surname;

    @Past(message = "Birthday should be in the past.")
    @NotNull(message = "Birthday is empty")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    @Email
    @NotNull(message = "E-mail is empty")
    @NotBlank(message = "E-mail is empty")
//    @UniqueElements(message = "E-mail is not unique on this web-site") // FIXME: удалить
    private String email;

    //    @NotNull(message = "Password is empty") // FIXME: удалить
    @Size(min = 8, max = 100, message = "Password should be more than 8 characters")
    private String password;

    private RoleOnSite role;

    private boolean userDeleted;

    private Set<AddressEntity> addressEntities;


}
