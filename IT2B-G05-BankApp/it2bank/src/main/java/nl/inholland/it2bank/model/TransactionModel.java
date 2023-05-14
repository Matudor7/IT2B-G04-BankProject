package nl.inholland.it2bank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@Entity
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private UserModel userPerforming;
    private AccountModel accountFrom;
    private AccountModel accountTo;
    private double amount;
    private Time time;

    public TransactionModel(long id, UserModel userPerforming, AccountModel accountFrom, AccountModel accountTo, double amount, Time time, String comment) {
        this.id = id;
        this.userPerforming = userPerforming;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.time = time;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserModel getUserPerforming() {
        return userPerforming;
    }

    public void setUserPerforming(UserModel userPerforming) {
        this.userPerforming = userPerforming;
    }

    public AccountModel getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(AccountModel accountFrom) {
        this.accountFrom = accountFrom;
    }

    public AccountModel getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(AccountModel accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;
}
