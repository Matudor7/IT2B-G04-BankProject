package nl.inholland.it2bank.model.dto;

import java.time.LocalDateTime;

public record TransactionDTO(int userPerforming, String accountFrom, String accountTo, double amount, LocalDateTime dateTime, String comment) {
}
