package nl.inholland.it2bank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //TODO for Tudor: Add daily limit and transaction limit as a double
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    private Long bsn;
    private String phoneNumber;
    private String email;
    private String password;
    private UserRoles role;

    public UserModel(String firstName, String lastName, long bsn, String phoneNumber, String email, String password, UserRoles role) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.bsn = bsn;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getBsn() {
        return bsn;
    }

    public void setBsn(Long bsn) {
        this.bsn = bsn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles roles) {
        this.role = role;

    }
}

