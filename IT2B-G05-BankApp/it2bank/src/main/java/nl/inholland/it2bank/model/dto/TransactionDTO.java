package nl.inholland.it2bank.model.dto;

import nl.inholland.it2bank.model.AccountModel;
import nl.inholland.it2bank.model.UserModel;

import java.sql.Time;

public record TransactionDTO(int userPerforming, String accountFrom, String accountTo, double amount, Time time, String comment) {
}
