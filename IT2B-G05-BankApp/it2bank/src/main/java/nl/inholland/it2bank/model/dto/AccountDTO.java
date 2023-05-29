package nl.inholland.it2bank.model.dto;

import nl.inholland.it2bank.model.AccountStatus;
import nl.inholland.it2bank.model.AccountType;

public record AccountDTO(String iban, int ownerId, Integer statusId, double amount, int absolutLimit, Integer typeId) {
}
