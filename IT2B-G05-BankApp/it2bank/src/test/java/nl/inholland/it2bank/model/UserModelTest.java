package nl.inholland.it2bank.model;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserModelTest {

    private UserModel user;

    @BeforeEach
    void init(){
        user = new UserModel();
    }

    @Test
    void newUserShouldNotBeNull() {
        Assertions.assertNotNull(user);
    }

    @Test
    void GetAndSetFirstNameShouldProvideValueToVariable(){
        user.setFirstName("First Name");
        Assertions.assertEquals("First Name", user.getFirstName());
    }

    @Test
    void GetAndSetLastNameShouldProvideValueToVariable(){
        user.setLastName("Last Name");
        Assertions.assertEquals("Last Name", user.getLastName());
    }

    @Test
    void GetAndSetBsnShouldProvideValueToVariable(){
        user.setBsn(12345678L);
        Assertions.assertEquals(12345678L, user.getBsn());
    }

    @Test
    void GetAndSetPhoneNumberShouldProvideValueToVariable(){
        user.setPhoneNumber("+31 6 1234 5678");
        Assertions.assertEquals("+31 6 1234 5678", user.getPhoneNumber());
    }

    @Test
    void GetAndSetEmailShouldProvideValueToVariable(){
        user.setEmail("email@address.com");
        Assertions.assertEquals("email@address.com", user.getEmail());
    }

    @Test
    void GetAndSetPasswordShouldProvideValueToVariable(){
        user.setPassword("mypass123");
        Assertions.assertEquals("mypass123", user.getPassword());
    }

    @Test
    void GetAndSetRoleShouldProvideValueToVariable(){
        user.setRole(UserRoles.User);
        Assertions.assertEquals(UserRoles.User, user.getRole());
    }

    @Test
    void GetAndSetTransactionLimitShouldProvideValueToVariable(){
        user.setTransactionLimit(50.0);
        Assertions.assertEquals(50.0, user.getTransactionLimit());
    }

    @Test
    void GetAndSetDailyLimitShouldProvideValueToVariable(){
        user.setDailyLimit(100.0);
        Assertions.assertEquals(100.0, user.getDailyLimit());
    }
}
