package nl.inholland.it2bank.model.dto;

public record BankAccountDTO(String iban, Integer ownerId, Integer statusId, Double balance, Integer absoluteLimit, Integer typeId) {
}
