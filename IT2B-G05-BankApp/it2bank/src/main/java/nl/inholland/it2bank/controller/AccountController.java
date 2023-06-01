package nl.inholland.it2bank.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import nl.inholland.it2bank.model.*;
import nl.inholland.it2bank.model.dto.AccountDTO;
import nl.inholland.it2bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
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
    @ApiOperation(value = "Get accounts", notes = "Retrieve accounts based on filters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved accounts", response = AccountModel.class, responseContainer = "List"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<List<AccountModel>> findAccountsByAttributes(
            @RequestParam(value = "iban", required = false) String iban,
            @RequestParam(value = "ownerId", required = false) Integer ownerId,
            @RequestParam(value = "statusId", required = false) Integer statusId,
            @RequestParam(value = "amount", required = false) Double amount,
            @RequestParam(value = "absoluteLimit", required = false) Integer absoluteLimit,
            @RequestParam(value = "typeId", required = false) Integer typeId
    ) {
        List<AccountModel> accounts = accountService.findAccountByAttributes(iban, ownerId, statusId, amount, absoluteLimit, typeId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    @ApiOperation(value = "Create Account", notes = "Successfully created account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created account", response = AccountModel.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<Object> addAccount(@RequestBody AccountDTO accountDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.addAccount(accountDto));
    }

    @PutMapping("{iban}")
    @ApiOperation(value = "Get accounts", notes = "Retrieve accounts based on filters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved accounts", response = AccountModel.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<Object> updateUserById(@PathVariable String iban, @RequestBody AccountDTO accountDto) {
        Optional<AccountModel> existingAccount = accountService.getAccountByIban(iban);

        if (existingAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AccountModel account = existingAccount.get();

        if (accountDto.ownerId() != 0) {
            account.setOwnerId(accountDto.ownerId());
        }
        if (accountDto.statusId() != null) {
            account.setStatusId(accountDto.statusId());
        }
        if (accountDto.amount() != 0) {
            account.setAmount(accountDto.amount());
        }
        if (accountDto.absoluteLimit() != 0) {
            account.setAbsoluteLimit(accountDto.absoluteLimit());
        }
        if (accountDto.typeId() != null) {
            account.setTypeId(accountDto.typeId());
        }

        AccountModel updatedAccount = accountService.saveAccount(account);

        return ResponseEntity.ok(updatedAccount);
    }
}
