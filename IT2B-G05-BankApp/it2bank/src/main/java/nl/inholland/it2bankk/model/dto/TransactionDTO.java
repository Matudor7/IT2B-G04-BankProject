package nl.inholland.it2bankk.model.dto;

import java.time.LocalTime;

public record TransactionDTO(int userPerforming, String accountFrom, String accountTo, double amount, LocalTime time, String comment) {
}
