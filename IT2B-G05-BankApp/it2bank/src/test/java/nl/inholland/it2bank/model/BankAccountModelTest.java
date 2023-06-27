package nl.inholland.it2bank.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountModelTest {
    @Test
    void newBankAccountShouldNotBeNull(){
        BankAccountModel account = new BankAccountModel();
        assertNotNull(account);
    }

    @Test
    void bankAccountOwnerIsNotNull(){
        BankAccountModel account = new BankAccountModel();
        account.setOwner(new UserModel());
        assertNotNull(account.getOwner());
    }

    @Test
    void bankAccountOwnerHasAccount(){
        BankAccountModel account = new BankAccountModel();
        UserModel user = new UserModel();
        account.setOwner(user);
        assertEquals(user, account.getOwner());
    }
    @Test
    void bankAccountBalanceIsUpdating(){
        double updatedBalance = 100.0;
        BankAccountModel account = new BankAccountModel();
        account.setBalance(updatedBalance);

        double retrievedBalance =  account.getBalance();

        assertEquals(updatedBalance, retrievedBalance);
    }
    @Test
    void bankAccountBalanceIsNotNull(){
        BankAccountModel account = new BankAccountModel();
        account.setBalance(0.0);
        assertNotNull(account.getBalance());
    }

}