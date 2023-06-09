package nl.inholland.it2bankk.model.dto;

import nl.inholland.it2bankk.model.UserModel;

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
