package nl.inholland.it2bank.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Integer userPerforming;
    private String accountFrom;
    private String accountTo;
    private Double amount;
    private LocalDateTime dateTime;
    private String comment;

    public TransactionModel(Integer userPerforming, String accountFrom, String accountTo, Double amount, LocalDateTime dateTime, String comment) {
        this.userPerforming = userPerforming;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.dateTime = dateTime;
        this.comment = comment;
    }

    public Integer getUserPerforming() {
        return userPerforming;
    }

    public void setUserPerforming(Integer userPerforming) {
        this.userPerforming = userPerforming;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
