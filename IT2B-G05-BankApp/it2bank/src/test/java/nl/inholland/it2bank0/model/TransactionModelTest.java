package nl.inholland.it2bank0.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionModelTest {

    private TransactionModel transactionModel;

    @BeforeEach
    void inti() {
        transactionModel = new TransactionModel();
    }

    @Test
    void newTransactionShouldNotBeNull() {
        Assertions.assertNotNull(transactionModel);
    }
}