package nl.inholland.it2bank0.model.dto;

import java.time.LocalDateTime;

public record TransactionDTO(Integer userPerforming, String accountFrom, String accountTo, double amount, LocalDateTime time, String comment) {
}
