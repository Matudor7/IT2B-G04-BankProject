package nl.inholland.it2bank.controller;

import lombok.extern.java.Log;
import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.model.dto.TransactionDTO;
import nl.inholland.it2bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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
    public ResponseEntity<List<TransactionModel>> getAllTransactions(
            @RequestParam(value = "userPerforming", required = false) Integer userPerforming,
            @RequestParam(value = "accountFrom", required = false) String accountFrom,
            @RequestParam(value = "accountTo", required = false) String accountTo,
            @RequestParam(value = "amount", required = false) Double amount,
            @RequestParam(value = "time", required = false) LocalTime time,
            @RequestParam(value = "comment", required = false) String comment
    ) {
        List<TransactionModel> transactions = transactionService.findTransactionByAttributes(userPerforming, accountFrom, accountTo, amount, time, comment);
        return ResponseEntity.ok(transactions);
    }


    @PostMapping
    public ResponseEntity<Object> addTransaction(@RequestBody TransactionDTO transactionDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.addTransaction(transactionDto));
    }
}
