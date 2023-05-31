package nl.inholland.it2bank.model.dto;

import nl.inholland.it2bank.model.AccountStatus;
import nl.inholland.it2bank.model.AccountType;

public record AccountDTO(String iban, Integer ownerId, Integer statusId, Double amount, Integer absolutLimit, Integer typeId) {
}
