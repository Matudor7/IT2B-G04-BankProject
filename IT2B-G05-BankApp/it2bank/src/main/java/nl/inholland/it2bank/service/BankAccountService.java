package nl.inholland.it2bank.service;

import jakarta.persistence.EntityNotFoundException;
import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.model.dto.BankAccountDTO;
import nl.inholland.it2bank.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccountModel> findAccountByAttributes(String iban, Integer ownerId, Integer statusId, Double amount, Integer absoluteLimit, Integer typeId) {
        return (List<BankAccountModel>) bankAccountRepository.findAccountByAttributes(iban, ownerId, statusId, amount, absoluteLimit, typeId);
    }


    public BankAccountModel addBankAccount(BankAccountDTO bankAccountDto) {
        validateBankAccountDto(bankAccountDto);
        return bankAccountRepository.save(this.mapObjectToAccount(bankAccountDto));
    }

    public Optional<BankAccountModel> getAccountByIban(String finalIban) {
        return bankAccountRepository.findByIban(finalIban);
    }

    public BankAccountModel saveAccount(BankAccountDTO bankAccountDto) {

        BankAccountModel account = mapObjectToAccount(bankAccountDto);

        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        if (!isIbanPresent(account.getIban())) {
            throw new IllegalArgumentException("Iban field must be filled.");
        }
        if (!allFieldsFilled(account)) {
            throw new IllegalArgumentException("All fields (amount, typeId, ownerId) must be filled.");
        }
        return Optional.of(bankAccountRepository.save(account)).orElseThrow(
                () -> new IllegalArgumentException("Something went wrong trying to update your account.")
        );
    }
    public void updateBankAccount(String iban, BankAccountDTO updatedBankAccount) {
        BankAccountModel existingBankAccount = this.getAccountByIban(iban).orElseThrow(()-> new EntityNotFoundException("Account not found"));

        existingBankAccount.setOwnerId(updatedBankAccount.ownerId());
        existingBankAccount.setStatusId(updatedBankAccount.statusId());
        existingBankAccount.setBalance(updatedBankAccount.balance());
        existingBankAccount.setAbsoluteLimit(updatedBankAccount.absoluteLimit());
        existingBankAccount.setTypeId(updatedBankAccount.typeId());

        bankAccountRepository.save(existingBankAccount);
    }


    private BankAccountModel mapObjectToAccount(BankAccountDTO bankAccountDto) {
        BankAccountModel account = new BankAccountModel();

        account.setIban(generateIban());
        account.setOwnerId(bankAccountDto.ownerId());
        account.setStatusId(bankAccountDto.statusId());
        account.setBalance(bankAccountDto.balance());
        account.setAbsoluteLimit(bankAccountDto.absoluteLimit());
        account.setTypeId(bankAccountDto.typeId());

        return account;
    }

    private boolean allFieldsFilled(BankAccountModel bankAccountModel) {
        return bankAccountModel.getBalance() != null && bankAccountModel.getTypeId() != null && bankAccountModel.getOwnerId() != null;
    }

    public String generateIban() {
        StringBuilder iban = new StringBuilder("NL");
        Random prefix = new Random();
        int max = 100;
        int min = 10;
        iban.append(prefix.nextInt(max - min) + min);
        iban.append("INHO");
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        iban.append(number);
        //input validation
        String finalIban = iban.toString();
        if (getAccountByIban(finalIban).isPresent()) {
            throw new IllegalArgumentException("Something went wrong generating your iban.");
        }
        return finalIban;
    }

    public boolean isIbanPresent(String ibanGiven) {
        if (ibanGiven == null) {
            throw new IllegalArgumentException("IBAN must be provided.");
        }
        return true;
    }

    private void validateBankAccountDto(BankAccountDTO bankAccountDto) {
        if (bankAccountDto == null) {
            throw new IllegalArgumentException("AccountDTO cannot be null.");
        }
        if (bankAccountDto.ownerId() == null) {
            throw new IllegalArgumentException("Owner ID is required.");
        }
        if (bankAccountDto.statusId() == null) {
            throw new IllegalArgumentException("Status ID is required.");
        }
        if (bankAccountDto.balance() == null) {
            throw new IllegalArgumentException("Amount is required.");
        }
        if (bankAccountDto.absoluteLimit() == null) {
            throw new IllegalArgumentException("Absolute limit is required.");
        }
        if (bankAccountDto.typeId() == null) {
            throw new IllegalArgumentException("Type ID is required.");
        }
    }
}