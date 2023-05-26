package nl.inholland.it2bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class AccountModel {

    @Id
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) { this.iban = iban; }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAbsolutLimit() {
        return absoluteLimit;
    }

    public void setAbsolutLimit(Integer absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountModel(String iban, Integer ownerId, AccountStatus status, Double amount, Integer absoluteLimit, AccountType type) {
        this.iban = iban;
        this.ownerId = ownerId;
        this.status = status;
        this.amount = amount;
        this.absoluteLimit = absoluteLimit;
        this.type = type;
    }

    @Column(nullable = false)
    private String iban;
    private Integer ownerId;
    private AccountStatus status;
    private Double amount;
    private Integer absoluteLimit;
    private AccountType type;

}
