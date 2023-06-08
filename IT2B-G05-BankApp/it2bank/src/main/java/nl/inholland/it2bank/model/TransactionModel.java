package nl.inholland.it2bank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private UserModel userPerforming;

    @OneToOne
    private BankAccountModel accountFrom;

    @OneToOne
    private BankAccountModel accountTo;

    @Column(nullable = false)
    private Double amount;
    private LocalDateTime time;
    private String comment;

    public TransactionModel(UserModel userPerforming, BankAccountModel accountFrom, BankAccountModel accountTo, Double amount, LocalDateTime time, String comment) {
        this.userPerforming = userPerforming;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.time = time;
        this.comment = comment;
    }

    public UserModel getUserPerforming() {
        return userPerforming;
    }

    public void setUserPerforming(UserModel userPerforming) {
        this.userPerforming = userPerforming;
    }

    public BankAccountModel getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(BankAccountModel accountFrom) {
        this.accountFrom = accountFrom;
    }

    public BankAccountModel getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(BankAccountModel accountTo) {
        this.accountTo = accountTo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
