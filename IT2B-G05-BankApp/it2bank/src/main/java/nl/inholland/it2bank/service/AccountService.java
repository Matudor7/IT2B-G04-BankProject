package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.AccountModel;
import nl.inholland.it2bank.model.dto.AccountDTO;
import nl.inholland.it2bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public List<AccountModel> getAllAccounts() { return (List<AccountModel>) accountRepository.findAll(); }

    public AccountModel addAccount(AccountDTO accountDto) {return accountRepository.save(this.mapObjectToAccount(accountDto)); }

    public Optional<AccountModel> findAccountByIban(String finalIban) { return accountRepository.findByIban(finalIban); }

    public AccountModel updateAccount(AccountModel account) {
        return Optional.of(accountRepository.save(account)).orElseThrow(
                () -> new IllegalArgumentException("Something went wrong trying to update your account."));
    }

    private AccountModel mapObjectToAccount(AccountDTO accountDto){
        AccountModel account = new AccountModel();

        account.setIban(accountDto.iban());
        account.setOwnerId(accountDto.ownerId());
        account.setStatus(accountDto.status());
        account.setAmount(accountDto.amount());
        account.setAbsolutLimit(accountDto.absolutLimit());
        account.setType(accountDto.type());

        return account;
    }
}
