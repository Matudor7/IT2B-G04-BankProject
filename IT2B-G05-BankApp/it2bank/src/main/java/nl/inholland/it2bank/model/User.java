package nl.inholland.it2bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private String firstName;
    private String lastName;
    private Long bsn;
    private Integer phoneNumber;
    private String email;
    private UserRoles role;
}
