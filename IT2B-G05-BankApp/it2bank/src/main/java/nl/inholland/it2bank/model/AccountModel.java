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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public AccountModel(String iban, Integer ownerId, Integer statusId, Double amount, Integer absoluteLimit, Integer typeId) {
        this.iban = iban;
        this.ownerId = ownerId;
        this.statusId = statusId;
        this.amount = amount;
        this.absoluteLimit = absoluteLimit;
        this.typeId = typeId;
    }

    @Column(nullable = false)
    private String iban;
    private Integer ownerId;
    private Integer statusId;
    private Double amount;
    private Integer absoluteLimit;
    private Integer typeId;

}
