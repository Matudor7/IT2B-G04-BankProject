package nl.inholland.it2bank.controller;


import lombok.extern.java.Log;
import nl.inholland.it2bank.model.dto.AccountDTO;
import nl.inholland.it2bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
