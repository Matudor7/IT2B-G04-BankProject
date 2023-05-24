package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.model.dto.TransactionDTO;
import nl.inholland.it2bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionModel> findByAttributes(Integer userPerforming, String accountFrom, String accountTo, Double amount, LocalTime time, String comment) {return (List<TransactionModel>) transactionRepository.findByAttributes(userPerforming, accountFrom, accountTo, amount, time, comment); }

    public TransactionModel addTransaction(TransactionDTO transactionDto) {return transactionRepository.save(this.mapObjectToTransaction(transactionDto)); }

    private TransactionModel mapObjectToTransaction(TransactionDTO transactionDto){
        TransactionModel transaction = new TransactionModel();

        transaction.setUserPerforming(transactionDto.userPerforming());
        transaction.setAccountFrom(transactionDto.accountFrom());
        transaction.setAccountTo(transactionDto.accountTo());
        transaction.setAmount(transactionDto.amount());
        transaction.setTime(transactionDto.time());
        transaction.setComment(transactionDto.comment());

        return transaction;
    }
}
