package nl.inholland.it2bank.model.dto;

import nl.inholland.it2bank.model.UserRoles;

public record UserDTO(String firstName, String lastName, Long bsn, Integer phoneNumber, String email, String password, UserRoles role) {
}
