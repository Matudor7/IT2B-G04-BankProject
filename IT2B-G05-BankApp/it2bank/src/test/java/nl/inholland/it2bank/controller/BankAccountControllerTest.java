package nl.inholland.it2bank.controller;

import nl.inholland.it2bank.config.ApiTestConfiguration;
import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import nl.inholland.it2bank.model.dto.BankAccountDTO;
import nl.inholland.it2bank.model.dto.UserDTO;
import nl.inholland.it2bank.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(BankAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ApiTestConfiguration.class)
public class BankAccountControllerTest {
    @MockBean
    BankAccountService bankAccountService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void getShouldReturnOkStatus() throws Exception{

        Mockito.when(bankAccountService.findAccountByAttributes(null, null, null, null, null, null))
                .thenReturn(List.of(
                        new BankAccountModel("NL01INHO0000000014", 3, 0, 10.00, 100, 1)
                ));

        mockMvc.perform(MockMvcRequestBuilders.get("/bankaccounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    void postShouldReturnCreatedStatus() throws Exception{
        BankAccountDTO bankAccountDTO = new BankAccountDTO("NL01INHO0000000014", 3, 0, 10.00, 100, 1);

        Mockito.when(bankAccountService.addBankAccount(bankAccountDTO))
                .thenReturn(new BankAccountModel());

        mockMvc.perform(MockMvcRequestBuilders.post("/bankaccounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"iban\": \"NL01INHO0000000014\",\"ownerId\": \"3\",\"statusId\": 0,\"balance\": \"10.00\",\"absoluteLimit\": \"100\",\"typeId\": \"1\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void putShouldReturnOkStatusWithBody() throws Exception{
        BankAccountModel bankAccount = new BankAccountModel("NL01INHO0000000001", 4, 0, 100000.00, 0, 1);
        bankAccount.setIban(bankAccount.getIban());
        mockMvc.perform(MockMvcRequestBuilders.put("/bankaccounts/{iban}", bankAccount.getIban())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"iban\": \"NL01INHO0000000001\",\"ownerId\": 4,\"statusId\": 0,\"balance\": 100000.00,\"absoluteLimit\": 0,\"typeId\": 1}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{\"iban\": \"NL01INHO0000000001\",\"ownerId\": 4,\"statusId\": 0,\"balance\": 100000.00,\"absoluteLimit\": 0,\"typeId\": 1}"));


    }
}
