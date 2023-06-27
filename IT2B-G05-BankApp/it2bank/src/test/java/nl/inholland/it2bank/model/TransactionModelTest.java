package nl.inholland.it2bank.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TransactionModelTest {

    private TransactionModel transaction;

    @BeforeEach
    void init() {
        transaction = new TransactionModel();
    }

    @Test
    void newTransactionShouldNotBeNull() {
        Assertions.assertNotNull(transaction);
    }

    @Test
    void getAndSetUserPerformingShouldProvideValueToVariable() {
        transaction.setUserPerforming(1);
        Assertions.assertEquals(1, transaction.getUserPerforming());
    }

    @Test
    void getAndSetAccountFromShouldProvideValueToVariable() {
        transaction.setAccountFrom("Account From");
        Assertions.assertEquals("Account From", transaction.getAccountFrom());
    }

    @Test
    void getAndSetAccountToShouldProvideValueToVariable() {
        transaction.setAccountTo("Account To");
        Assertions.assertEquals("Account To", transaction.getAccountTo());
    }

    @Test
    void getAndSetAmountShouldProvideValueToVariable() {
        transaction.setAmount(100.0);
        Assertions.assertEquals(100.0, transaction.getAmount());
    }

    @Test
    void getAndSetDateTimeShouldProvideValueToVariable() {
        LocalDateTime dateTime = LocalDateTime.now();
        transaction.setDateTime(dateTime);
        Assertions.assertEquals(dateTime, transaction.getDateTime());
    }

    @Test
    void getAndSetCommentShouldProvideValueToVariable() {
        transaction.setComment("Transaction Comment");
        Assertions.assertEquals("Transaction Comment", transaction.getComment());
    }
}

