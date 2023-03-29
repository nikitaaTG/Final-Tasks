package org.example.FinalProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.FinalProject.enums.RoleOnSite;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"email_address"}))
public class UserEntity {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Name is empty")
    @Size(min = 2, max = 55, message = "Name must contain from 2 to 55 characters")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Surname is empty")
    @Size(min = 2, max = 55, message = "Surname must contain from 2 to 55 characters")
    private String surname;

    @Past
    @Column(name = "date_of_birth")
    @NotNull(message = "Birthday is empty")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    @Column(name = "email_address", unique = true)
    @Email
    @NotEmpty(message = "E-mail is empty")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password is empty")
    @Size(min = 8, max = 100, message = "Password should be more than 8 characters")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleOnSite role;

    @Column(name = "deleted")
    @NotNull
    private boolean userDeleted;

    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<AddressEntity> addresses;

}
