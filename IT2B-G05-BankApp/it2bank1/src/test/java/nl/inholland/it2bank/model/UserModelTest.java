package nl.inholland.it2bank.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserModelTest {

    private UserModel userModel;

    @BeforeEach
    void init() {
        userModel = new UserModel();
    }

    @Test
    void newUserShouldNotBeNull() {
        Assertions.assertNotNull(userModel);
    }
}