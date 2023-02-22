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

    @Column(name = "role")
    @NotNull
    private RoleOnSite role;

    //Don't forget to change type to boolean in the DB.
    @Column(name = "deleted")
    @NotNull
    private boolean userDeleted;



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Address> adresses;

    public Set<Address> getAdresses() {
        return adresses;
    }

    public void setAdresses(Set<Address> adresses) {
        this.adresses = adresses;
    }



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}