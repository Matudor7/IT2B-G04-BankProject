package nl.inholland.it2bank.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountModelTest {

    private BankAccountModel bankAccountModel;

    @BeforeEach
    void init() {
        bankAccountModel = new BankAccountModel();
    }

    @Test
    void newBankAccountShouldNotBeNull() {
        Assertions.assertNotNull(bankAccountModel);
    }
}