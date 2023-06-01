package nl.inholland.it2bank.model.dto;

public record AccountDTO(String iban, Integer ownerId, Integer statusId, Double amount, Integer absoluteLimit, Integer typeId) {
}
