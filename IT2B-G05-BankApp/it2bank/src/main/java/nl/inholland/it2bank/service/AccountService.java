package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.AccountModel;
import nl.inholland.it2bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountModel> getAllAccounts() {return (List<AccountModel>) accountRepository.findAll(); }
}
