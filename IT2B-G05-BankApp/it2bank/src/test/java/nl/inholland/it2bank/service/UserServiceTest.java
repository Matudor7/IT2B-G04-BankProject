package nl.inholland.it2bank.service;

import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.EntityNotFoundException;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.repository.UserRepository;
import nl.inholland.it2bank.util.JwtTokenProvider;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUsersShouldReturnAllUsers() {
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
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull())).thenReturn(users);

        List<UserModel> retrievedUsers = userService.findUserByAttributes(null, null, null, null, null, null, null, null, null);

        Assertions.assertTrue(retrievedUsers.contains(user1));
        Assertions.assertTrue(retrievedUsers.contains(user2));
        Assertions.assertEquals(2, retrievedUsers.size());
    }

    @Test
    void getUserByIdShouldReturnUser(){
        UserModel user = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);
        user.setId(1);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserModel foundUser = userService.getUserById(1);

        Assertions.assertEquals(user, foundUser);
    }

    @Test
    void getUserByIdShouldThrowIfUserNotFound(){
        UserModel user = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);
        user.setId(1);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(2));
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

    @Test
    void updateUserShouldChangeUser(){
        UserModel existingUser = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);
        UserDTO userDto = new UserDTO("AnotherFirstname", "AnotherLastname", 1233456789L, "06123435678", "email@address.com", "AnotherPassword", "User", 500.0, 1000.0);

        existingUser.setFirstName(userDto.firstName());
        existingUser.setLastName(userDto.lastName());
        existingUser.setBsn(userDto.bsn());
        existingUser.setEmail(userDto.email());
        existingUser.setPhoneNumber(userDto.phoneNumber());
        existingUser.setPassword(userDto.password());
        existingUser.setRole(UserRoles.valueOf(userDto.role()));
        existingUser.setDailyLimit(userDto.dailyLimit());
        existingUser.setTransactionLimit(userDto.transactionLimit());

        Mockito.when(userRepository.save(existingUser)).thenReturn(existingUser);
        Mockito.when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        userService.updateUser(existingUser.getId(), userDto);

        UserModel updatedUser = new UserModel(existingUser.getFirstName(), existingUser.getLastName(), existingUser.getBsn(), existingUser.getPhoneNumber(), existingUser.getEmail(), existingUser.getPassword(), existingUser.getRole(), existingUser.getTransactionLimit(), existingUser.getDailyLimit());

        Assertions.assertEquals(updatedUser, existingUser);
    }

    @Test
    void updateUserShouldThrowIfTransactionLimitIsHigherThanDailyLimit(){
        UserModel existingUser = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);
        UserDTO userDto = new UserDTO("AnotherFirstname", "AnotherLastname", 1233456789L, "06123435678", "email@address.com", "AnotherPassword", "User", 500.0, 100.0);

        existingUser.setFirstName(userDto.firstName());
        existingUser.setLastName(userDto.lastName());
        existingUser.setBsn(userDto.bsn());
        existingUser.setEmail(userDto.email());
        existingUser.setPhoneNumber(userDto.phoneNumber());
        existingUser.setPassword(userDto.password());
        existingUser.setRole(UserRoles.valueOf(userDto.role()));
        existingUser.setDailyLimit(userDto.dailyLimit());
        existingUser.setTransactionLimit(userDto.transactionLimit());

        Mockito.when(userRepository.save(existingUser)).thenReturn(existingUser);
        Mockito.when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.updateUser(existingUser.getId(), userDto));
    }

    @Test
    void mapObjectToUserShouldMap(){
        UserModel existingUser = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);
        UserDTO userDto = new UserDTO("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", "Employee", 50.0, 100.0);

        UserModel newUser = new UserModel();

        newUser.setFirstName(userDto.firstName());
        newUser.setLastName(userDto.lastName());
        newUser.setBsn(userDto.bsn());
        newUser.setEmail(userDto.email());
        newUser.setPassword(userDto.password());
        newUser.setPhoneNumber(userDto.phoneNumber());
        newUser.setRole(UserRoles.valueOf(userDto.role()));
        newUser.setTransactionLimit((userDto.transactionLimit() == null) ? 50 : userDto.transactionLimit());
        newUser.setDailyLimit((userDto.dailyLimit() == null) ? 100 : userDto.dailyLimit());

        Assertions.assertEquals(newUser, existingUser);
    }

    @Test
    void deleteUserShouldInvokeRepository(){
        UserModel user = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.User, 50.0, 100.0);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getId());
    }
    @Test
    void deleteUserWithNonUserRoleShouldThrowException(){
        UserModel user = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(user.getId()));
    }

    @Test
    void loginShouldReturnToken() throws Exception{
        UserModel user = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);

        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String password = "Password";

        Mockito.when(bCryptPasswordEncoder.matches("Password", user.getPassword())).thenReturn(true);
        Mockito.when(jwtTokenProvider.createToken(user.getEmail(), user.getRole())).thenReturn("Token");

        Assertions.assertEquals("Token", userService.login(user.getEmail(), user.getPassword()));
    }

    @Test
    void invalidLoginShouldThrowException() throws Exception{
        UserModel user = new UserModel("Firstname", "Lastname", 123345678L, "0612343567", "email@address.com", "Password", UserRoles.Employee, 50.0, 100.0);

        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String password = "Password1";

        Mockito.when(bCryptPasswordEncoder.matches("Password1", user.getPassword())).thenReturn(false);
        Mockito.when(jwtTokenProvider.createToken(user.getEmail(), user.getRole())).thenReturn("Token");

        Assertions.assertThrows(AuthenticationException.class, () -> userService.login(user.getEmail(), user.getPassword()));
    }
}
