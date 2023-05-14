package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionModel> getAllTransactions() {return (List<TransactionModel>) transactionRepository.findAll(); }
}
