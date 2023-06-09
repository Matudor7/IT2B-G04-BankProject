package nl.inholland.it2bank.model.dto;

import jakarta.validation.constraints.NotNull;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;

import java.util.List;


public record UserDTO(
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull long bsn,
        @NotNull String phoneNumber,
        @NotNull String email,
        @NotNull String password,
        @NotNull String role,
        @NotNull Double transactionLimit,
        @NotNull Double dailyLimit
)
{
    public UserDTO(UserModel userModel) {
        this(userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getBsn(),
                userModel.getPhoneNumber(),
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getRole().toString(),
                userModel.getTransactionLimit(),
                userModel.getDailyLimit());
    }
}
