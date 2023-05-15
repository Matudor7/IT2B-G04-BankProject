package nl.inholland.it2bank.controller;

import lombok.extern.java.Log;
import nl.inholland.it2bank.model.dto.TransactionDTO;
import nl.inholland.it2bank.service.TransactionService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> getAllTransactions() { return ResponseEntity.ok(transactionService.getAllTransactions());}

    @PostMapping
    public ResponseEntity<Object> addTransaction(@RequestBody TransactionDTO transactionDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.addTransaction(transactionDto));
    }
}
