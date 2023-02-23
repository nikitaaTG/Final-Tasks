package org.example.FinalProject.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.FinalProject.enums.RoleOnSite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "name")
    @NotEmpty(message = "Name is empty")
    @Size(min = 2, max = 55, message = "Name must contain from 2 to 55 characters")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Surname is empty")
    @Size(min = 2, max = 55, message = "Surname must contain from 2 to 55 characters")
    private String surname;

    @Column(name = "date_of_birth")
    @NotNull(message = "Birthday is empty")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    @Column(name = "email_address", unique = true)
    @Email
    @NotEmpty(message = "E-mail is empty")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Password is empty")
    private String password;

    @Column(insertable=false, updatable=false, name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "role")
    @NotNull
    private RoleOnSite role;

    //Don't forget to change type to boolean in the DB.
    @Column(name = "deleted")
    @NotNull
    private boolean userDeleted;



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Address> addresses;

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> adresses) {
        this.addresses = adresses;
    }

}
