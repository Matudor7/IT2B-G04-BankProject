package nl.inholland.it2bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class AccountModel {

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAbsolutLimit() {
        return absolutLimit;
    }

    public void setAbsolutLimit(int absolutLimit) {
        this.absolutLimit = absolutLimit;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountModel(String iban, int ownerId, AccountStatus status, double amount, int absolutLimit, AccountType type) {
        this.iban = iban;
        this.ownerId = ownerId;
        this.status = status;
        this.amount = amount;
        this.absolutLimit = absolutLimit;
        this.type = type;
    }

    @Column(nullable = false)
    private String iban;
    private int ownerId;
    private AccountStatus status;
    private double amount;
    private int absolutLimit;
    private AccountType type;

}
