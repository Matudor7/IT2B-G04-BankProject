package nl.inholland.it2bank.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.java.Log;
import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.model.dto.TransactionDTO;
import nl.inholland.it2bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("transactions")
@Log
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @ApiOperation(value = "Get transactions", notes = "Retrieve transactions based on filters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved transactions", response = TransactionModel.class, responseContainer = "List"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<List<TransactionModel>> findTransactionsByAttributes(
            @RequestParam(value = "userPerforming", required = false) Integer userPerforming,
            @RequestParam(value = "accountFrom", required = false) String accountFrom,
            @RequestParam(value = "accountTo", required = false) String accountTo,
            @RequestParam(value = "amount", required = false) Double amount,
            @RequestParam(value = "dateTime", required = false) LocalDateTime time,
            @RequestParam(value = "comment", required = false) String comment
    ) {
        List<TransactionModel> transactions = transactionService.findTransactionByAttributes(userPerforming, accountFrom, accountTo, amount, time, comment);
        return ResponseEntity.ok(transactions);
    }



    @PostMapping
    @ApiOperation(value = "Post transactions", notes = "Perform a transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transaction was successful", response = TransactionModel.class, responseContainer = "List"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Malformed request syntax")
    })
    public ResponseEntity<Object> addTransaction(@RequestBody TransactionDTO transactionDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.addTransaction(transactionDto));
    }
}
