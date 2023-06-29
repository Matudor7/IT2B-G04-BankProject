package nl.inholland.it2bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class BankAccountModel {

    @Id
    private String iban;

    @Column(insertable = false, updatable = false)
    private Long ownerId;

    private Integer statusId;
    private Double balance;
    private Integer absoluteLimit;
    private Integer typeId;

    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "id")
    @JsonBackReference
    private UserModel owner;

    public BankAccountModel(String iban, Long ownerId, Integer statusId, Double balance, Integer absoluteLimit, Integer typeId) {
        this.iban = iban;
        this.ownerId = ownerId;
        this.statusId = statusId;
        this.balance = balance;
        this.absoluteLimit = absoluteLimit;
        this.typeId = typeId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
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

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }
}
