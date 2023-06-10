package nl.inholland.it2bank.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import nl.inholland.it2bank.model.*;
import nl.inholland.it2bank.model.dto.BankAccountDTO;
import nl.inholland.it2bank.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bankaccounts")
@Log
public class BankAccountController {

    @Autowired
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<String> getBankAccountByFirstName(@RequestParam("first_name") String firstName) {
        String iban = String.valueOf(bankAccountService.getIbanByFirstName(firstName));
        if (iban != null) {
            return ResponseEntity.ok(iban);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Get accounts", notes = "Retrieve accounts based on filters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved accounts", response = BankAccountModel.class, responseContainer = "List"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<List<BankAccountModel>> findAccountsByAttributes(
            @RequestParam(value = "iban", required = false) String iban,
            @RequestParam(value = "ownerId", required = false) Integer ownerId,
            @RequestParam(value = "statusId", required = false) Integer statusId,
            @RequestParam(value = "balance", required = false) Double balance,
            @RequestParam(value = "absoluteLimit", required = false) Integer absoluteLimit,
            @RequestParam(value = "typeId", required = false) Integer typeId,
            @RequestParam(value = "firstname", required = false) String firstName
    ) {
        List<BankAccountModel> accounts = bankAccountService.findAccountByAttributes(iban, ownerId, statusId, balance, absoluteLimit, typeId, firstName);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    @ApiOperation(value = "Create Account", notes = "Successfully created account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created account", response = BankAccountModel.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Access token is missing or invalid"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<Object> addBankAccount(@RequestBody BankAccountDTO bankAccountDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankAccountService.addBankAccount(bankAccountDto));
    }

    @PutMapping("{iban}")
    @ApiOperation(value = "Get accounts", notes = "Retrieve accounts based on filters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved accounts", response = BankAccountModel.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })

    public ResponseEntity<Object> updateBankAccountByIban(@PathVariable String iban, @RequestBody BankAccountDTO bankAccountDto) {
        Optional<BankAccountModel> existingAccount = bankAccountService.getAccountByIban(iban);

        if (existingAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

//        BankAccountModel account = existingAccount.get();
//
//        if (bankAccountDto.ownerId() != 0) {
//            account.setOwnerId(bankAccountDto.ownerId());
//        }
//        if (bankAccountDto.statusId() != null) {
//            account.setStatusId(bankAccountDto.statusId());
//        }
//        if (bankAccountDto.balance() != 0) {
//            account.setBalance(bankAccountDto.balance());
//        }
//        if (bankAccountDto.absoluteLimit() != 0) {
//            account.setAbsoluteLimit(bankAccountDto.absoluteLimit());
//        }
//        if (bankAccountDto.typeId() != null) {
//            account.setTypeId(bankAccountDto.typeId());
//        }

        BankAccountModel updatedAccount = bankAccountService.saveAccount(bankAccountDto);

        return ResponseEntity.ok(updatedAccount);
    }
}
