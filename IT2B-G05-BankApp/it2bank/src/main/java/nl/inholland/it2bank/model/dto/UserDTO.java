package nl.inholland.it2bank.model.dto;

import nl.inholland.it2bank.model.UserRoles;

public record UserDTO(String firstName, String lastName, long bsn, String phoneNumber, String email, String password, int roleId) {
}
