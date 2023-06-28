package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.dto.TransactionDTO;
import nl.inholland.it2bank.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    BankAccountService bankAccountService;

    @Mock
    UserService userService;

    @InjectMocks
    TransactionService transactionService = new TransactionService(transactionRepository, bankAccountService, userService);

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(transactionRepository, bankAccountService, userService);
    }

    @Test
    void findTransactionsShouldReturnAllTransactions() {
        TransactionModel transaction1 = new TransactionModel(1, "NL01INHO0000000022", "NL01INHO0000000030", 500.0, LocalDateTime.now(), "comment1");
        TransactionModel transaction2 = new TransactionModel(2, "NL01INHO0000000030", "NL01INHO0000000022", 200.0, LocalDateTime.now(), "comment2");
        List<TransactionModel> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        Mockito.when(transactionRepository.findTransactionByAttributes(
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull())).thenReturn(transactions);

        List<TransactionModel> retrievedTransactions = transactionService.findTransactionByAttributes(null, null, null, null, null, null);

        Assertions.assertTrue(retrievedTransactions.contains(transaction1));
        Assertions.assertTrue(retrievedTransactions.contains(transaction2));
        Assertions.assertEquals(2, retrievedTransactions.size());
    }


    @Test
    void addTransactionShouldReturnNewTransaction() throws Exception {

        TransactionDTO transactionDto = new TransactionDTO(1, "NL01INHO0000000022", "NL01INHO0000000030", 500.0, "comment");
        BankAccountModel accountFrom = new BankAccountModel("NL01INHO0000000022", 1, 0, 1000.0, 0, 1);
        BankAccountModel accountTo = new BankAccountModel("NL01INHO0000000030", 2, 0, 2000.0, 0, 1);
        TransactionModel newTransaction = new TransactionModel(1, accountFrom.getIban(), accountTo.getIban(), 500.0, LocalDateTime.now(), "comment");
        UserModel userModel = new UserModel();

        transactionService = new TransactionService(transactionRepository, bankAccountService, userService);

        Mockito.when(bankAccountService.getAccountByIban(Mockito.eq(transactionDto.accountFrom()))).thenReturn(Optional.of(accountFrom));
        Mockito.when(bankAccountService.getAccountByIban(Mockito.eq(transactionDto.accountTo()))).thenReturn(Optional.of(accountTo));
        Mockito.when(userService.getUserById(Mockito.eq(accountFrom.getOwnerId()))).thenReturn(userModel);

        Mockito.when(transactionRepository.save(Mockito.any(TransactionModel.class))).thenReturn(newTransaction);

        TransactionModel transaction = transactionService.addTransaction(transactionDto);

        Assertions.assertEquals(newTransaction, transaction);
    }
}
