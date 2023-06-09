package nl.inholland.it2bank0.model.dto;

import nl.inholland.it2bank0.model.UserModel;

public record UserDTO(String firstName, String lastName, long bsn, String phoneNumber, String email, String password, String role, Double transactionLimit, Double dailyLimit) {
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
