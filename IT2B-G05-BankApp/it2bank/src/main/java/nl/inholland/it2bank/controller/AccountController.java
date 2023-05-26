package nl.inholland.it2bank.controller;


import lombok.extern.java.Log;
import nl.inholland.it2bank.model.AccountModel;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.dto.AccountDTO;
import nl.inholland.it2bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("accounts")
@Log
public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllAccounts() { return ResponseEntity.ok(accountService.getAllAccounts()); }

    @PostMapping
    public ResponseEntity<Object> addAccount(@RequestBody AccountDTO accountDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.addAccount(accountDto));
    }

    @GetMapping("{iban}")
    public ResponseEntity<Object> getIbanById(@PathVariable String iban){
        return ResponseEntity.ok().body(accountService.getAccountByIban(iban));
    }

    @PutMapping("{iban}")
    public ResponseEntity<Object> updateUserById(@PathVariable String iban, @RequestBody AccountDTO accountDto) {
        Optional<AccountModel> existingAccount = accountService.getAccountByIban(iban);

        if (existingAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AccountModel account = existingAccount.get();

        if (accountDto.ownerId() != 0) {
            account.setOwnerId(accountDto.ownerId());
        }
        if (accountDto.status() != null) {
            account.setStatus(accountDto.status());
        }
        if (accountDto.amount() != 0) {
            account.setAmount(accountDto.amount());
        }
        if (accountDto.absolutLimit() != 0) {
            account.setAbsolutLimit(accountDto.absolutLimit());
        }
        if (accountDto.type() != null) {
            account.setType(accountDto.type());
        }

        AccountModel updatedAccount = accountService.saveAccount(account);

        return ResponseEntity.ok(updatedAccount);
    }
}
