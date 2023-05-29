package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.AccountModel;
import nl.inholland.it2bank.model.AccountStatus;
import nl.inholland.it2bank.model.AccountType;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.dto.AccountDTO;
import nl.inholland.it2bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AccountIbanService accountIbanService;

    public AccountService(AccountRepository accountRepository, AccountIbanService accountIbanService) {
        this.accountRepository = accountRepository;
        this.accountIbanService = accountIbanService;
    }

    public List<AccountModel> findAccountByAttributes(String iban, Integer ownerId, Integer statusId, Double amount, Integer absoluteLimit, Integer typeId) { return (List<AccountModel>) accountRepository.findAccountByAttributes(iban, ownerId, statusId, amount, absoluteLimit, typeId); }

    public AccountModel addAccount(AccountDTO accountDto) {return accountRepository.save(this.mapObjectToAccount(accountDto)); }

    public Optional<AccountModel> getAccountByIban(String finalIban) { return accountRepository.findByIban(finalIban); }

    public AccountModel saveAccount(AccountModel account){
        return accountRepository.save(account);
    }

    public AccountModel updateAccount(AccountModel account) {
        return Optional.of(accountRepository.save(account)).orElseThrow(
                () -> new IllegalArgumentException("Something went wrong trying to update your account."));
    }

    private AccountModel mapObjectToAccount(AccountDTO accountDto){
        AccountModel account = new AccountModel();

        String finalIban = accountIbanService.generateIban();

//        while (getAccountByIban(finalIban) != null){
//            finalIban = accountIbanService.generateIban();
//            throw new IllegalArgumentException("Something went wrong generating your iban.");
//        }
        account.setIban(finalIban);
        account.setOwnerId(accountDto.ownerId());
        account.setStatusId(accountDto.statusId());
        account.setAmount(accountDto.amount());
        account.setAbsolutLimit(accountDto.absolutLimit());
        account.setTypeId(accountDto.typeId());

        return account;
    }
}
