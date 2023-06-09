package nl.inholland.it2bank.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record TransactionDTO(int userPerforming, String accountFrom, String accountTo, double amount, LocalDateTime time, String comment) {
}
