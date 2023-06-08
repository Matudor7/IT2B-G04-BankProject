package nl.inholland.it2bank.model.dto;

import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.model.UserModel;

import java.time.LocalTime;

public record TransactionDTO(UserModel userPerforming, BankAccountModel accountFrom, BankAccountModel accountTo, double amount, LocalTime time, String comment) {
}
