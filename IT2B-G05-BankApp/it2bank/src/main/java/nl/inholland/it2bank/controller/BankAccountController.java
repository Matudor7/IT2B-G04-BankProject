package nl.inholland.it2bank.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import nl.inholland.it2bank.model.*;
import nl.inholland.it2bank.model.dto.BankAccountDTO;
import nl.inholland.it2bank.model.dto.ExceptionDTO;
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
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "last_name", required = false) String lastName
    ) {
        List<BankAccountModel> accounts;
        if (firstName != null) {
            accounts = bankAccountService.findAccountByFirstName(firstName);
        } else if (lastName != null) {
            accounts = bankAccountService.findAccountByLastName(lastName);
        }
        else {
            accounts = bankAccountService.findAccountByAttributes(iban, ownerId, statusId, balance, absoluteLimit, typeId);
        }
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
        try{
            bankAccountService.updateBankAccount(iban, bankAccountDto);
            return ResponseEntity.status(200).body(bankAccountDto);
        }catch(Exception e){
            return handleException(e);
        }
    }

    private ResponseEntity handleException(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getClass().getName(), e.getMessage());
        return ResponseEntity.status(400).body(exceptionDTO);
    }
}
