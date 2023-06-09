package nl.inholland.it2bank.service;

import io.jsonwebtoken.lang.Assert;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    //TODO: No need to test this, we have GetById which works better for this testing.
    @Test
    void findUserByEmployeeShouldReturnOneUser() {
        UserModel user1 = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);
        UserModel user2 = new UserModel("AnotherFirstname", "AnotherLastname", 1233456789L, "06123435678", "another_email@address.com", "AnotherPassword", UserRoles.User, 500.0, 1000.0);
        List<UserModel> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Mockito.when(userRepository.findUserByAttributes(
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.eq(UserRoles.Employee),
                Mockito.isNull(),
                Mockito.isNull())).thenReturn(users);

        List<UserModel> employees = userService.findUserByAttributes(null, null, null, null, null, null, UserRoles.Employee, null, null);

        Assertions.assertTrue(employees.contains(user1));
        Assertions.assertFalse(employees.contains(user2));
        Assertions.assertEquals(1, employees.size());
    }

    @Test
    void addUserShouldReturnNewUser(){
        UserDTO userDto = new UserDTO("AnotherFirstname", "AnotherLastname", 1233456789L, "06123435678", "another_email@address.com", "AnotherPassword", "User", 500.0, 1000.0);

        UserModel newUser = userService.mapObjectToUser(userDto);
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);

        UserModel savedUser = userService.addUser(userDto);

        Assertions.assertEquals(newUser, savedUser);
    }

    @Test
    void addUserShouldThrowExceptionIfEmailAlreadyExists(){
        UserModel existingUser = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);
        UserDTO newUser = new UserDTO("AnotherFirstname", "AnotherLastname", 1233456789L, "06123435678", "email@address.com", "AnotherPassword", "User", 500.0, 1000.0);

        Mockito.when(userRepository.existsByEmail(existingUser.getEmail())).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser));
    }
    //TODO: Finish testing the other methods.
}
