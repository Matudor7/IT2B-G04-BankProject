package nl.inholland.it2bank.model.dto;

public record TransactionDTO(String firstName, String lastName, long bsn, String phoneNumber, String email, String password, int roleId) {
}
