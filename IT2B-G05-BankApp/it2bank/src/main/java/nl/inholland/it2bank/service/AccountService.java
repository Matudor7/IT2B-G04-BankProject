package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.AccountModel;
import nl.inholland.it2bank.model.dto.AccountDTO;
import nl.inholland.it2bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<AccountModel> findAccountByAttributes(String iban, Integer ownerId, Integer statusId, Double amount, Integer absoluteLimit, Integer typeId) {
        return (List<AccountModel>) accountRepository.findAccountByAttributes(iban, ownerId, statusId, amount, absoluteLimit, typeId);
    }


    public AccountModel addAccount(AccountDTO accountDto) {
        validateAccountDto(accountDto);
        return accountRepository.save(this.mapObjectToAccount(accountDto));
    }

    public Optional<AccountModel> getAccountByIban(String finalIban) {
        return accountRepository.findByIban(finalIban);
    }

    public AccountModel saveAccount(AccountModel account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        if (!isIbanPresent(account.getIban())) {
            throw new IllegalArgumentException("Iban field must be filled.");
        }
        if (!allFieldsFilled(account)) {
            throw new IllegalArgumentException("All fields (amount, typeId, ownerId) must be filled.");
        }
        return Optional.of(accountRepository.save(account)).orElseThrow(
                () -> new IllegalArgumentException("Something went wrong trying to update your account.")
        );
    }

    private AccountModel mapObjectToAccount(AccountDTO accountDto) {
        AccountModel account = new AccountModel();

        account.setIban(generateIban());
        account.setOwnerId(accountDto.ownerId());
        account.setStatusId(accountDto.statusId());
        account.setAmount(accountDto.amount());
        account.setAbsoluteLimit(accountDto.absoluteLimit());
        account.setTypeId(accountDto.typeId());

        return account;
    }

    private boolean allFieldsFilled(AccountModel accountModel) {
        return accountModel.getAmount() != null && accountModel.getTypeId() != null && accountModel.getOwnerId() != null;
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

    private void validateAccountDto(AccountDTO accountDto) {
        if (accountDto == null) {
            throw new IllegalArgumentException("AccountDTO cannot be null.");
        }
        if (accountDto.ownerId() == null) {
            throw new IllegalArgumentException("Owner ID is required.");
        }
        if (accountDto.statusId() == null) {
            throw new IllegalArgumentException("Status ID is required.");
        }
        if (accountDto.amount() == null) {
            throw new IllegalArgumentException("Amount is required.");
        }
        if (accountDto.absoluteLimit() == null) {
            throw new IllegalArgumentException("Absolute limit is required.");
        }
        if (accountDto.typeId() == null) {
            throw new IllegalArgumentException("Type ID is required.");
        }
    }
}