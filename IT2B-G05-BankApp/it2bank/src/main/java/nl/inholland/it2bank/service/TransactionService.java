package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.model.TransactionModel;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.dto.BankAccountDTO;
import nl.inholland.it2bank.model.dto.TransactionDTO;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountService bankAccountService;

    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository, BankAccountService bankAccountService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.bankAccountService = bankAccountService;
        this.userService = userService;
    }

    public List<TransactionModel> findTransactionByAttributes(Integer userPerforming, String accountFrom, String accountTo, Double amount, LocalDateTime dateTime, String comment) {return (List<TransactionModel>) transactionRepository.findTransactionByAttributes(userPerforming, accountFrom, accountTo, amount, dateTime, comment); }

    public TransactionModel addTransaction(TransactionDTO transactionDto) {
        TransactionModel transactionModel = this.mapObjectToTransaction(transactionDto);

        UserModel userModel = userService.getUserById(transactionModel.getUserPerforming());
        BankAccountModel accountFrom = bankAccountService.getAccountByIban(transactionModel.getAccountFrom()).orElseThrow();
        BankAccountModel accountTo = bankAccountService.getAccountByIban(transactionModel.getAccountTo()).orElseThrow();

        try {
            updateDailyLimit(userModel, transactionModel, accountFrom);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateBalance(accountFrom, accountTo, transactionModel);

        return transactionRepository.save(transactionModel);
    }

    private TransactionModel mapObjectToTransaction(TransactionDTO transactionDto){
        TransactionModel transaction = new TransactionModel();

        transaction.setUserPerforming(transactionDto.userPerforming());
        transaction.setAccountFrom(transactionDto.accountFrom());
        transaction.setAccountTo(transactionDto.accountTo());
        transaction.setAmount(transactionDto.amount());
        transaction.setDateTime(transactionDto.dateTime());
        transaction.setComment(transactionDto.comment());

        return transaction;
    }

    private void updateBalance(BankAccountModel accountFrom, BankAccountModel accountTo, TransactionModel transactionModel) {
        accountFrom.setBalance(accountFrom.getBalance() - transactionModel.getAmount());
        accountTo.setBalance(accountTo.getBalance() + transactionModel.getAmount());

        BankAccountDTO accountFromDto = new BankAccountDTO(accountFrom);
        BankAccountDTO accountToDto = new BankAccountDTO(accountTo);

        bankAccountService.saveAccount(accountFromDto);
        bankAccountService.saveAccount(accountToDto);
    }

    private void updateDailyLimit(UserModel userModel, TransactionModel transactionModel, BankAccountModel bankAccountModel) throws Exception {
        if (!checkDailyLimit(userModel, transactionModel)) {
            throw new Exception("Daily limit exceeded!");
        }

        if (!checkTransactionLimit(userModel, transactionModel)) {
            throw new Exception("Transaction limit exceeded!");
        }

        if (!checkAbsoluteLimit(bankAccountModel, transactionModel)) {
            throw new Exception("Absolute limit exceeded!");
        }

        userModel.setDailyLimit(userModel.getDailyLimit() - transactionModel.getAmount());
        UserDTO userDTO = new UserDTO(userModel);
        userService.updateUser(userModel.getId(), userDTO);
    }

    private boolean checkDailyLimit(UserModel userModel, TransactionModel transactionModel) {
        if (userModel.getDailyLimit() >= transactionModel.getAmount())
            return true;
        return false;
    }
    private boolean checkTransactionLimit(UserModel userModel, TransactionModel transactionModel) {
        if (userModel.getTransactionLimit() >= transactionModel.getAmount())
            return true;
        return false;
    }

    private boolean checkAbsoluteLimit(BankAccountModel accountFrom, TransactionModel transactionModel) {
        if (accountFrom.getBalance() - transactionModel.getAmount() >= accountFrom.getAbsoluteLimit())
            return true;
        return false;
    }
}
