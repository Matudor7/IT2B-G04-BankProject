package nl.inholland.it2bank.service;

import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.BankAccountDTO;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.repository.BankAccountRepository;
import nl.inholland.it2bank.repository.UserRepository;
import nl.inholland.it2bank.util.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountServiceTest {
    @Mock
    BankAccountRepository bankAccountRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    BankAccountService bankAccountService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }
@Test
    void findBankAccountsShouldReturnAllBankAccounts(){
        BankAccountModel bankie1 = new BankAccountModel("NL01INHO0000000014", 3, 0, 1000.0, 0, 1);
        BankAccountModel bankie2 = new BankAccountModel("NL01INHO0000000001", 4, 0, 1000000.0, 0, 1);
        List<BankAccountModel> bankies = new ArrayList<>();
        bankies.add(bankie1);
        bankies.add(bankie2);

        Mockito.when(bankAccountRepository.findAccountByAttributes(
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull(),
                Mockito.isNull())).thenReturn(bankies);

        List<BankAccountModel> retrievedBankAccounts = bankAccountService.findAccountByAttributes(null, null, null, null, null, null);

        Assertions.assertTrue(retrievedBankAccounts.contains(bankie1));
        Assertions.assertTrue(retrievedBankAccounts.contains(bankie2));
        Assertions.assertEquals(2, retrievedBankAccounts.size());
    }
    @Test
    void addBankAccountShouldReturnNewBankie() {
        BankAccountDTO bankieDto = new BankAccountDTO("NL01INHO0000000022", 1, 0, 10000.0, 0, 0);

        BankAccountModel newBankie = new BankAccountModel("NL01INHO0000000022", 1, 0, 10000.0, 0, 0);

        BankAccountService bankAccountService = new BankAccountService(bankAccountRepository, userRepository);
        // use the userRepository.findById() method
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new UserModel()));

        Mockito.when(bankAccountRepository.save(Mockito.any(BankAccountModel.class))).thenReturn(newBankie);

        BankAccountModel bankAccount = bankAccountService.addBankAccount(bankieDto);

        Assertions.assertEquals(newBankie, bankAccount);
    }

    @Test
    void getAccountByIbanShouldReturnBankAccount() {
        String iban = "NL01INHO0000000022";
        BankAccountModel expectedAccount = new BankAccountModel("NL01INHO0000000022", 1, 0, 10000.0, 0, 0);

        Mockito.when(bankAccountRepository.findByIban(iban)).thenReturn(Optional.of(expectedAccount));

        Optional<BankAccountModel> actualAccount = bankAccountService.getAccountByIban(iban);

        assertTrue(actualAccount.isPresent());
        assertEquals(expectedAccount, actualAccount.get());
    }
    @Test
    void updateBankAccountShouldUpdateAccountProperties(){
        BankAccountModel existingBankie = new BankAccountModel("NL01INHO0000000022", 1, 1, 20000.0, 1000, 1);
        BankAccountDTO bankieDto = new BankAccountDTO("NL01INHO0000000022", 1, 1, 20000.0, 0, 1);

        existingBankie.setIban(bankieDto.iban());
        existingBankie.setOwnerId(bankieDto.ownerId());
        existingBankie.setStatusId(bankieDto.statusId());
        existingBankie.setBalance(bankieDto.balance());
        existingBankie.setAbsoluteLimit(bankieDto.absoluteLimit());
        existingBankie.setTypeId(bankieDto.typeId());

        Mockito.when(bankAccountRepository.save(existingBankie)).thenReturn(existingBankie);
        Mockito.when(bankAccountRepository.findByIban(existingBankie.getIban())).thenReturn(Optional.of(existingBankie));

        bankAccountService.updateBankAccount(existingBankie.getIban(), bankieDto);

        BankAccountModel updatedBankie = new BankAccountModel(existingBankie.getIban(), existingBankie.getOwnerId(), existingBankie.getStatusId(), existingBankie.getBalance(), existingBankie.getAbsoluteLimit(), existingBankie.getTypeId());

        Assertions.assertEquals(updatedBankie, existingBankie);
    }
    @Test
    void findAccountByFullNameShouldReturnAccounts() {
        // Mock data
        String firstName = "Revolver";
        String lastName = "Ocelot";
        List<BankAccountModel> expectedAccounts = new ArrayList<>();
        expectedAccounts.add(new BankAccountModel("NL01INHO0000000030", 0, 0, 1000.0, 0, 1));
        expectedAccounts.add(new BankAccountModel("NL01INHO0000000031", 1, 0, 1000.0, 0, 1));
        expectedAccounts.add(new BankAccountModel("NL01INHO0000000032", 2, 0, 1000.0, 0, 1));
        expectedAccounts.add(new BankAccountModel("NL01INHO0000000033", 3, 0, 1000.0, 0, 1));
        expectedAccounts.add(new BankAccountModel("NL01INHO0000000034", 4, 0, 1000.0, 0, 1));



        BankAccountRepository bankAccountRepository = Mockito.mock(BankAccountRepository.class);
        Mockito.when(bankAccountRepository.findAccountsByFullName(firstName, lastName)).thenReturn(expectedAccounts);
        // Create an instance of the BankAccountService and inject the mock repository
        BankAccountService bankAccountService = new BankAccountService(bankAccountRepository, userRepository);

        List<BankAccountModel> actualAccounts = bankAccountService.findAccountByFullName(firstName, lastName);
        Assertions.assertIterableEquals(expectedAccounts, actualAccounts);
        // Verify that the bankAccountRepository.findAccountsByFullName method was called
        Mockito.verify(bankAccountRepository).findAccountsByFullName(firstName, lastName);
    }


    @Test
    void findAccountByFirstNameShouldThrowExceptionIfNoAccountsFound() {
        // Mock data
        String firstName = "John";

        // Mock the behavior of the bankAccountRepository to return an empty list
        BankAccountRepository bankAccountRepository = Mockito.mock(BankAccountRepository.class);
        Mockito.when(bankAccountRepository.findAccountsByFirstName(firstName)).thenReturn(new ArrayList<>());

        // Create an instance of the BankAccountService and inject the mock repository
        BankAccountService bankAccountService = new BankAccountService(bankAccountRepository, userRepository);

        // Call the findAccountByFirstName method and assert that it throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> bankAccountService.findAccountByFirstName(firstName));

        // Verify that the bankAccountRepository.findAccountsByFirstName method was called
        Mockito.verify(bankAccountRepository).findAccountsByFirstName(firstName);
    }





}