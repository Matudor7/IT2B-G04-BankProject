package nl.inholland.it2bank.model.dto;

import nl.inholland.it2bank.model.BankAccountModel;

public record BankAccountDTO(String iban, Integer ownerId, Integer statusId, Double balance, Integer absoluteLimit, Integer typeId) {
    public BankAccountDTO(BankAccountModel bankAccountModel) {
        this(bankAccountModel.getIban(),
                bankAccountModel.getOwnerId(),
                bankAccountModel.getStatusId(),
                bankAccountModel.getBalance(),
                bankAccountModel.getAbsoluteLimit(),
                bankAccountModel.getTypeId());
    }
}
