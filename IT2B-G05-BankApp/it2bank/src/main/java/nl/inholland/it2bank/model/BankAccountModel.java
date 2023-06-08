package nl.inholland.it2bank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BankAccountModel {

    @Id
    private String iban;
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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getAbsoluteLimit() {
        return absoluteLimit;
    }

    public void setAbsoluteLimit(Integer absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public BankAccountModel(String iban, Integer ownerId, Integer statusId, Double balance, Integer absoluteLimit, Integer typeId) {
        this.iban = iban;
        this.ownerId = ownerId;
        this.statusId = statusId;
        this.balance = balance;
        this.absoluteLimit = absoluteLimit;
        this.typeId = typeId;
    }

    @Column(nullable = false)
    private Integer ownerId;
    private Integer statusId;
    private Double balance;
    private Integer absoluteLimit;
    private Integer typeId;
}
